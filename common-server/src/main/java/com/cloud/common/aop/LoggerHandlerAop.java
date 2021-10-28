package com.cloud.common.aop;

import com.cloud.common.aop.annotation.MethodLogger;
import com.cloud.common.enums.LogTypeEnum;
import com.cloud.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-06-27 17:01
 */
@Aspect
@Component
@Slf4j
public class LoggerHandlerAop {

    @Pointcut("@annotation(com.cloud.common.aop.annotation.MethodLogger)")
    public void loggerHandler(){}

    @Around("loggerHandler()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        MethodLogger methodLogger = methodSignature.getMethod().getAnnotation(MethodLogger.class);
        String methodName = joinPoint.getSignature().getName();

        String args = null;
        try {
            if (LogTypeEnum.FULL == methodLogger.logType() || LogTypeEnum.PARAM == methodLogger.logType()) {
                args = JsonUtil.toString(joinPoint.getArgs());
                log.info("method: {}, args: {}", methodName, args);
            }
        } catch (Exception var12) {
            log.warn("method: {}, args log error {}", methodName, var12.getLocalizedMessage());
        }

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable var10) {
            log.error("method: {}, error {}", methodName, ExceptionUtils.getMessage(var10));
            throw var10;
        }

        try {
            if (LogTypeEnum.FULL == methodLogger.logType() || LogTypeEnum.RETURN == methodLogger.logType()) {
                long elapsedTime = System.currentTimeMillis() - start;
                log.info("method: {}, result: {}, span: {}", new Object[]{methodName, JsonUtil.toString(result), elapsedTime});
            }
        } catch (Exception var11) {
            log.warn("method: {}, return log error {}", methodName, var11.getLocalizedMessage());
        }

        return result;
    }
}
