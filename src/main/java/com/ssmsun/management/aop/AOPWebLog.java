package com.ssmsun.management.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
@Aspect
public class AOPWebLog {

    private static final Logger logger = LoggerFactory.getLogger(AOPWebLog.class);

    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.ssmsun.management.service.impl.*.*(..))";

    @Around(AOP_POINTCUT_EXPRESSION)
    public Object Log(ProceedingJoinPoint point) throws Throwable {
        logger.info("");
        Object object=point.proceed();

        return object;
    }
}
