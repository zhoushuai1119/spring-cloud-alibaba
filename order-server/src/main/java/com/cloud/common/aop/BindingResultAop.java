package com.cloud.common.aop;

import com.cloud.common.beans.KeyValuePair;
import com.cloud.common.beans.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.LogUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>采用AOP的方式处理参数问题
 */
@Component
@Aspect
@Slf4j
public class BindingResultAop {

    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final ExecutableValidator validator = factory.getValidator().forExecutables();
    private static Pattern p = Pattern.compile("[\u4e00-\u9fa5]");

    @Pointcut("@annotation(com.cloud.common.annotation.ParamCheck)")
    public void paramCheckMethod(){}

    @Around("paramCheckMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获得切入的方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //取参数，如果没参数，那肯定不校验了
        Object[] objects = joinPoint.getArgs();
        if (objects.length == 0) {
            return joinPoint.proceed();
        }
        BindingResult bindingResult = null;
        for(Object arg:objects){
            if(arg instanceof BindingResult){
                bindingResult = (BindingResult) arg;
            }
        }
        /**************************校验封装好的javabean**********************/
        if(bindingResult != null){
            if(bindingResult.hasErrors()){
                Class<?> returnType = method.getReturnType();
                String errorInfo=bindingResult.getFieldError().getDefaultMessage();
                log.error(errorInfo);
                if(isContainChinese(errorInfo)){
                    Constructor<?> constructor = returnType.getConstructor(int.class, String.class);
                    return constructor.newInstance(-100, errorInfo);
                }else{
                    Constructor<?> constructor = returnType.getConstructor(KeyValuePair.class);
                    return constructor.newInstance(ReturnCode.PARAM_ERROR);
                }
            }
        }
        /**************************校验普通参数*************************/
        //  获得切入目标对象
        Object target = joinPoint.getThis();
        // 执行校验，获得校验结果
        Set<ConstraintViolation<Object>> validResult = validMethodParams(target, method, objects);
        //如果有校验不通过的
        if (!validResult.isEmpty()) {
            Class<?> returnType = method.getReturnType();
            // 获得方法的参数名称
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            for(ConstraintViolation<Object> constraintViolation : validResult) {
                //获得校验的参数路径信息
                PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath();
                //获得校验的参数位置
                int paramIndex = pathImpl.getLeafNode().getParameterIndex();
                // 获得校验的参数名称
                String paramName = parameterNames[paramIndex];
                log.info("校验参数:"+paramName);
                //校验信息
                log.info("校验信息:"+constraintViolation.getMessage());
            }
            //返回第一条
            Constructor<?> constructor = returnType.getConstructor(int.class, String.class);
            return constructor.newInstance(-100, validResult.iterator().next().getMessage());
        }
        return joinPoint.proceed();
    }

    /**
     * 判断提示是否包含中文
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    private <T> Set<ConstraintViolation<T>> validMethodParams(T obj, Method method, Object[] params) {
        return validator.validateParameters(obj, method, params);
    }
}
