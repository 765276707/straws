package com.gitee.pristine.security.authc;

import com.gitee.pristine.common.result.ErrorResponse;
import com.gitee.pristine.common.result.ErrorStatus;
import com.gitee.pristine.common.utils.RsaUtil;
import com.gitee.pristine.security.annotation.Anonymous;
import com.gitee.pristine.security.config.SecurityProperties;
import com.gitee.pristine.security.context.SecurityContext;
import com.gitee.pristine.security.context.Subject;
import com.gitee.pristine.security.jwts.JwtClaims;
import com.gitee.pristine.security.jwts.JwtUtil;
import com.gitee.pristine.security.utils.AnnotationUtil;
import com.gitee.pristine.security.utils.ResponseUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;

/**
 * 认证拦截器
 * @author Pristine Xu
 * @date 2022/2/3 8:12
 * @description:
 */
public class AuthcInterceptor implements HandlerInterceptor {

    private final SecurityProperties securityProperties;

    @Value("${server.servlet.context-path}")
    private String servletContextPath;

    // 路径匹配器
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    // 没有配置token key，则默认使用该值
    private final static String DEFAULT_TOKEN_KEY_IN_HEADER = "Authorization";

    public AuthcInterceptor(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 排除哪些接口不需要令牌
        // 1.配置类的
        String uri = this.getEffectRequestURI(request);
        if (this.matchPath(uri)) {
            return true;
        }

        // 2.注解的
        boolean hasAnonymous = AnnotationUtil.hasMethodAnnotation(handler, Anonymous.class);
        if (hasAnonymous) {
            return true;
        }

        // 需要进行身份令牌校验
        String token = this.obtainTokenFromHeader(request);
        if (!StringUtils.isEmpty(token))
        {
            try
            {
                JwtClaims jwtClaims = JwtUtil.getJwtClaims(token, this.getPublicKey());
                // subject 在此处是 username
                String username = jwtClaims.getSubject();
                // 保存到上下文中
                SecurityContext.setContext(new Subject(username));
            }
            catch (JwtException | InvalidKeySpecException | NoSuchAlgorithmException e)
            {
                // 令牌过期或无效
                ResponseUtil.writeJSON(response, HttpStatus.UNAUTHORIZED,
                        new ErrorResponse(ErrorStatus.UNAUTHORIZED));
                return false;
            }
        }
        else
        {
            // 未携带令牌
            ResponseUtil.writeJSON(response, HttpStatus.UNAUTHORIZED,
                    new ErrorResponse(ErrorStatus.UNAUTHORIZED));
            return false;
        }

        // 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContext.clearContext();
    }

    protected String obtainTokenFromHeader(HttpServletRequest request) {
        // 获取令牌配置信息
        String tokenKey = this.securityProperties.getToken().getHeader();
        if (StringUtils.isEmpty(tokenKey)) {
            tokenKey = DEFAULT_TOKEN_KEY_IN_HEADER;
        }
        // 从Header中获取令牌
        return request.getHeader(tokenKey);
    }

    /**
     * 获取 ServletContextPath 之后的请求路径
     * @param request
     * @return
     */
    private String getEffectRequestURI(HttpServletRequest request) {
        // 解析
        String effectURI = request.getRequestURI();
        if (servletContextPath != null) {
            effectURI = effectURI.replace(servletContextPath, "");
        }
        return effectURI;
    }

    /**
     * 匹配路径无需身份验证
     * @param uri
     * @return
     */
    private boolean matchPath(String uri) {
        Set<String> anonymousUris = this.securityProperties.getToken().getAnonymousUris();
        if (anonymousUris.isEmpty()) {
            return false;
        }
        boolean result = false;
        for (String reg : anonymousUris) {
            if (antPathMatcher.match(reg, uri)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 获取令牌公钥
     * @return
     */
    private PublicKey getPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return RsaUtil.string2PublicKey(this.securityProperties.getToken().getRsaPublicKey());
    }

}
