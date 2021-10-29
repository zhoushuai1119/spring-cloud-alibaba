package com.cloud.common.aop;

import com.cloud.common.aop.annotation.NewAuthV2;
import com.cloud.common.beans.request.PageQueryRequest;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @author qiankx
 * @date 2021-09-07 15:02
 **/
@Aspect
@Slf4j
@Component
public class NewAuthV2Handler {

    @Pointcut("@annotation(com.cloud.common.aop.annotation.NewAuthV2)")
    public void additional() {

    }

    @Around("additional()")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        NewAuthV2 newAuth = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(NewAuthV2.class);
        Object[] args = joinPoint.getArgs();
        List<Object> objects = Lists.newArrayList();
        for (Object arg : args) {
            if (arg instanceof PageQueryRequest) {
                objects.add(((PageQueryRequest) arg).getModel());
            } else {
                objects.add(arg);
            }
        }

        for (Object arg : objects) {
            if (Objects.nonNull(ReflectionUtils.findField(arg.getClass(), newAuth.agentNoProperty(), String.class))) {
                String methodName = "set" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,
                        newAuth.agentNoProperty());
                Method method = ReflectionUtils.findMethod(arg.getClass(), methodName, String.class);
                if (Objects.nonNull(method)) {
                    ReflectionUtils.invokeMethod(method, arg,newAuth.agentNoProperty());
                }
            }
        }
        return joinPoint.proceed();
    }

}
