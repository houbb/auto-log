package com.github.houbb.auto.log.spring.aop;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.core.support.interceptor.AutoLogMethodInterceptor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Component;

/**
 * @author binbin.hou
 * @since 0.0.3
 */
//@Component
public class AutoLogMethodAdvisor extends AbstractPointcutAdvisor {

    /**
     * 获取切面
     *
     * 获取表达式：
     *
     * <pre>
     *
     *     AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
     *     pointcut.setExpression("切入点表达式");         //基于method级别的注解
     *
     * </pre>
     * @return 切面
     * @since 0.0.3
     */
    @Override
    public Pointcut getPointcut() {
        return AnnotationMatchingPointcut.forMethodAnnotation(AutoLog.class);
    }

    @Override
    public Advice getAdvice() {
        return new AutoLogMethodInterceptor();
    }

}
