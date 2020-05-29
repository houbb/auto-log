package com.github.houbb.auto.log.spring.config;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.core.support.interceptor.AutoLogMethodInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 自动日志 aop 配置
 *
 * https://blog.csdn.net/u013905744/article/details/91364736
 * @author binbin.hou
 * @since 0.0.3
 */
@Configuration
@ComponentScan(basePackages = "com.github.houbb.auto.log.spring")
public class AutoLogAopConfig {

    /**
     *
     * 建议实现
     * @return 实现
     * @since 0.0.3
     */
    @Bean
    public DefaultPointcutAdvisor advisor() {
        MethodInterceptor interceptor = new AutoLogMethodInterceptor();
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(AutoLog.class, true);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
    }

}
