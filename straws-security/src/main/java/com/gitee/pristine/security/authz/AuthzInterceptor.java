package com.gitee.pristine.security.authz;

import com.gitee.pristine.common.result.ErrorResponse;
import com.gitee.pristine.common.result.ErrorStatus;
import com.gitee.pristine.security.annotation.HasRole;
import com.gitee.pristine.security.context.SecurityContext;
import com.gitee.pristine.security.context.Subject;
import com.gitee.pristine.security.utils.AnnotationUtil;
import com.gitee.pristine.security.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 健全拦截器
 * @author Pristine Xu
 * @date 2022/2/3 4:44
 * @description:
 */
public class AuthzInterceptor implements HandlerInterceptor {

    private final SecurityService securityService;

    public AuthzInterceptor(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取上下文的 Subject
        Subject subject = SecurityContext.getContext();
        if (subject != null) {
            // 非匿名访问，判断是否需要进行权限校验
            HasRole hasRole = AnnotationUtil.getMethodAnnotation(handler, HasRole.class);
            if (hasRole != null) {
                // 接口上有该注解，进行权限校验
                String role = hasRole.value();
                boolean access = securityService.hasRole(subject, role);
                if (!access) {
                    // 权限不足
                    ResponseUtil.writeJSON(response, HttpStatus.FORBIDDEN, new ErrorResponse(ErrorStatus.INSUFFICIENT_AUTHORITY));
                    return false;
                }
            }
        }

        // 放行
        return true;
    }

}
