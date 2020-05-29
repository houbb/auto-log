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
 * 日志标识。  TraceId
 * 日志输出级别。（默认为 info）
 *
 * 异常能处理类，callback 后期拓展
 * 其他：
 * 输出信息  ip 等等。
 *
 * 不强依赖 spring
 * 可以 CGLIB OP Proxy 实现
 *
 * SPI 深入
 * 日志输出深入 ILog String
 *
 * ToString() 的实现策略。
 *
 * @author binbin.hou
 * @since 0.0.1
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
    boolean costTime() default false;

}