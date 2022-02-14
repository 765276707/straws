package com.gitee.pristine.aop.log.core;

import com.gitee.pristine.aop.log.anno.AopLog;
import com.gitee.pristine.security.context.SecurityContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 日志的切面
 * @author Pristine Xu
 * @date 2022/2/7 17:42
 * @description:
 */
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LoggerAspectj {

    @Resource
    private LoggerStorage loggerStorage;

    /**
     * 切入点为 Logger 注解
     */
    @Pointcut("@annotation(com.gitee.pristine.aop.log.anno.AopLog)")
    public void pointCut(){}


    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 获取注解
        AopLog logger = this.getAnnotation(pjp);
        // 获取登录用户
        String opuser = logger.user();
        if (opuser.equals("")) {
            opuser = SecurityContext.getContext().getUsername();
        }
        String desc = logger.desc();
        String type = logger.type().getValue();
        String color = logger.type().getColor();
        String method = pjp.getSignature().getName();
        // 继续执行方法内业务
        Object result = pjp.proceed();
        // 保存日志信息
        loggerStorage.saveLog(
                new Log(type, color, desc, method, new Date(), opuser)
        );
        // 返回解析后的结果
        return result;
    }


    /**
     * 获取注解
     * @param point
     * @return
     */
    private AopLog getAnnotation(JoinPoint point) {
        Signature signature = point.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            return method!=null ? method.getAnnotation(AopLog.class) : null;
        }
        return null;
    }

}
