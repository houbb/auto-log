package com.github.houbb.auto.log.annotation;

import com.github.houbb.auto.log.api.IAutoLog;
import com.github.houbb.auto.log.api.IAutoLogInterceptor;

import java.lang.annotation.*;

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
 * 添加方法描述参数。
 *
 *
 * 配置优先级：类注解 < 方法注解
 *
 * @author binbin.hou
 * @since 0.0.1
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface AutoLog {

    /**
     * 是否启用
     * @return 结果
     * @since 0.0.10
     */
    boolean enable() default true;

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

    /**
     * 是否输出异常信息
     * @return 是否
     * @since 0.0.6
     */
    boolean exception() default true;

    /**
     * 慢日志阈值
     *
     * 当值小于 0 时，不进行慢日志统计。
     * 当值大于等于0时，当前值只要大于等于这个值，就进行统计。
     * @return 阈值
     * @since 0.0.4
     */
    long slowThresholdMills() default -1;

    /**
     * 方法描述
     * @return 方法描述
     * @since 0.0.7
     */
    String description() default "";

    /**
     * 是否显示 traceId 信息
     * @return 是否
     * @since 0.0.8
     */
    boolean traceId() default true;

    /**
     * 具体的拦截器实现
     * @since 0.0.10
     */
    Class<? extends IAutoLogInterceptor>[] interceptor() default {IAutoLogInterceptor.class};

}