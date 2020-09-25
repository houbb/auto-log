package com.github.houbb.auto.log.api;

import com.github.houbb.auto.log.annotation.TraceId;

import java.util.Map;

/**
 * 拦截器上下文
 * @author binbin.hou
 * @since 0.0.10
 */
public interface ITraceIdInterceptorContext {

    /**
     * 设置日志唯一标识的注解
     * @return 日志唯一标识
     * @since 0.0.10
     */
    TraceId traceId();

    /**
     * 设置值
     * @param key key
     * @param value 值
     * @since 0.0.10
     */
    void put(String key, Object value);

    /**
     * 获取值
     * @param key key
     * @return 结果
     * @since 0.0.10
     */
    Object get(String key);

}
