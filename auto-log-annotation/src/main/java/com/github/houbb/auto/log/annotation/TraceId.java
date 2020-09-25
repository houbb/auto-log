package com.github.houbb.auto.log.annotation;

import com.github.houbb.auto.log.api.IAutoLogInterceptor;
import com.github.houbb.auto.log.api.ITraceIdInterceptor;
import com.github.houbb.id.api.Id;

import java.lang.annotation.*;

/**
 * @author binbin.hou
 * @since 0.0.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
@Documented
public @interface TraceId {

    /**
     * 是否启用
     * @return 结果
     * @since 0.0.10
     */
    boolean enable() default true;

    /**
     * id 的生成策略
     * @return 策略
     * @since 0.0.8
     */
    Class<? extends Id> id() default Id.class;

    /**
     * 当前进程不存在日志标识的时候才设置
     * @return 是否
     * @since 0.0.8
     */
    boolean putIfAbsent() default false;

    /**
     * 具体的拦截器实现
     * @since 0.0.10
     */
    Class<? extends ITraceIdInterceptor>[] interceptor() default {ITraceIdInterceptor.class};

}
