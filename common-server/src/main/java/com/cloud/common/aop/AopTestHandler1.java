package com.cloud.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description: 测试AOP通知执行顺序
 * @Author: ZhouShuai
 * @Date: 2021-06-27 17:01
 *
 * spring4 执行顺序：
 * 正常情况： 1：环绕通知之前  2：@Before前置通知  3：方法调用  4：环绕通知之后  5：@After后置通知  6：@AfterReturning返回后通知
 * 异常情况： 1：环绕通知之前  2：@Before前置通知  3: 方法调用  4：@After后置通知  4: @AfterThrowing异常通知
 *
 * spring5 执行顺序：
 * 正常情况： 1：环绕通知之前  2：@Before前置通知  3：方法调用  4：@AfterReturning返回后通知  5：@After后置通知 6：@Around环绕通知之后
 * 异常情况： 1：环绕通知之前  2：@Before前置通知  3：方法调用  4：@AfterThrowing异常通知  5: @After后置通知
 *
 * 多个切面下的执行顺序，先通过@Order 主键排序，值越低优先级越高
 * 先执行高优先级的  1：AopTestHandler1环绕通知之前  2：AopTestHandler1 @Before前置通知
 * 再执行低优先级的  3：AopTestHandler2环绕通知之前  4：AopTestHandler2 @Before前置通知  5：AopTestHandler2 方法调用  6：AopTestHandler2 @AfterReturning返回后通知
 *                7：AopTestHandler2 @After后置通知 8：AopTestHandler2 @Around环绕通知之后
 * 低优先级执行完毕之后再返回高优先级继续执行
 *                9：AopTestHandler1 @AfterReturning返回后通知  10：AopTestHandler1 @After后置通知 11：AopTestHandler1 @Around环绕通知之后
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class AopTestHandler1 {

    @Pointcut("@annotation(com.cloud.common.aop.annotation.AopOrderTest)")
    public void loggerHandler(){}

    @Around("loggerHandler()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //log.info("执行 AopTestHandler2 AOP logAround 方法。。。。。。。。。。。");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = joinPoint.getSignature().getName();
        log.info("AopTestHandler1 : doAround-1");
        Object result = joinPoint.proceed();
        log.info("AopTestHandler1 : doAround-2");
        log.info("AopTestHandler1 result:{}",result);
        return result;
    }

    @Before("loggerHandler()")
    public void before(JoinPoint point) {
        log.info("AopTestHandler1 : doBefore");
    }

    @After("loggerHandler()")
    public void after() {
        log.info("AopTestHandler1 : doAfter");
    }


    @AfterReturning(pointcut = "loggerHandler()", returning = "returnValue")
    public void afterReturning(JoinPoint point,Object returnValue) {
        log.info("AopTestHandler1 : doAfterReturning");
    }


    @AfterThrowing(pointcut = "loggerHandler()", throwing = "e")
    public void afterThrowing(Exception e) {
        log.info("AopTestHandler1 : doAfterThrowing");
    }

}
