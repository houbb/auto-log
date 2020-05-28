package com.github.houbb.auto.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动注解
 *
 * 考虑自定义输出的格式？
 *
 * slowMillsThreshold 1000
 *
 * @author binbin.hou
 * @since 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoLog {

    /**
     * 输出参数
     * @return 参数
     * @since 0.0.1
     */
    boolean param() default true;

    /**
     * 是否输出结果
     * @return 结果
     * @since 0.0.1
     */
    boolean result() default true;

    /**
     * 是否输出时间
     * @return 耗时
     * @since 0.0.1
     */
    boolean time() default false;

}
