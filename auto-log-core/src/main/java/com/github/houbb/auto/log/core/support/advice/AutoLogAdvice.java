package com.github.houbb.auto.log.core.support.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 切面拦截器
 *
 * @author binbin.hou
 * @since 0.3.0
 */
public class AutoLogAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        methodInvocation.getMethod();

        methodInvocation.proceed();

        return null;
    }

}
