package com.github.houbb.auto.log.spring.aop;

import com.github.houbb.auto.log.core.core.impl.AbstractAutoLogContext;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 切面拦截器
 *
 * @author binbin.hou
 * @since 0.3.0
 */
public class AutoLogAdviceContext extends AbstractAutoLogContext {

    public static AutoLogAdviceContext newInstance() {
        return new AutoLogAdviceContext();
    }

    private MethodInvocation methodInvocation;

    public MethodInvocation methodInvocation() {
        return methodInvocation;
    }

    public AutoLogAdviceContext methodInvocation(MethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
        return this;
    }

    @Override
    public Object process() throws Throwable {
        return methodInvocation.proceed();
    }
}
