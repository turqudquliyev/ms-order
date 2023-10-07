package az.ingress.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class LoggingAspect {

    @Before("@annotation(az.ingress.aop.annotation.Loggable)")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("ActionLog.{} called with param {}", methodName, args);
    }

    @AfterReturning(pointcut = "@annotation(az.ingress.aop.annotation.Loggable)", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("ActionLog.{} returned {}", methodName, result);
    }
}