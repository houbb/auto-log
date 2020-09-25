package com.github.houbb.auto.log.spring.annotation;

import com.github.houbb.auto.log.core.core.IAutoLog;
import com.github.houbb.auto.log.core.core.impl.SimpleAutoLog;
import com.github.houbb.auto.log.spring.config.AutoLogAopConfig;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用自动日志注解
 * @author binbin.hou
 * @since 0.0.3
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AutoLogAopConfig.class)
@EnableAspectJAutoProxy
public @interface EnableAutoLog {

    /**
     * 具体的实现方式
     * @since 0.0.9
     */
    Class<? extends IAutoLog> [] value() default {SimpleAutoLog.class};

}
