package com.cloud.common.aop;

import com.cloud.common.annotation.MethodLogger;
import com.cloud.common.enums.LogTypeEnum;
import com.cloud.common.utils.JsonUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-06-27 17:01
 */
@Aspect
@Component
public class LoggerHandlerAop {

    @Around("@annotation(methodLogger)")
    public Object logAround(ProceedingJoinPoint joinPoint, MethodLogger methodLogger) throws Throwable {
        long start = System.currentTimeMillis();
        Logger log = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        String methodName = joinPoint.getSignature().getName();

        String args;
        try {
            if (LogTypeEnum.FULL == methodLogger.logType() || LogTypeEnum.PARAM == methodLogger.logType()) {
                args = JsonUtil.toString(joinPoint.getArgs());
                log.info("method: {}, args: {}", methodName, args);
            }
        } catch (Exception var12) {
            log.warn("method: {}, args log error {}", methodName, var12.getLocalizedMessage());
        }

        args = null;

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
