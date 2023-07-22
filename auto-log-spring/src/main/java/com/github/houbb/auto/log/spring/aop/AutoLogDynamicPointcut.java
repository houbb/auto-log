package com.github.houbb.auto.log.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * 动态配置的切面
 * 自动日志输出 aop
 * @author binbin.hou
 * @since 0.3.0
 */
@Configuration
@Aspect
//@EnableAspectJAutoProxy
public class AutoLogDynamicPointcut {

    /**
     * 切面设置，直接和 spring 的配置对应 ${}，可以从 properties 或者配置中心读取。更加灵活
     */
    @Value("${auto.log.pointcut:@within(com.github.houbb.auto.log.annotation.AutoLog)||@annotation(com.github.houbb.auto.log.annotation.AutoLog)}")
    private String pointcut;

    @Bean("autoLogPointcutAdvisor")
    public AspectJExpressionPointcutAdvisor autoLogPointcutAdvisor() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(pointcut);
        advisor.setAdvice(new AutoLogAdvice());
        return advisor;
    }

}
