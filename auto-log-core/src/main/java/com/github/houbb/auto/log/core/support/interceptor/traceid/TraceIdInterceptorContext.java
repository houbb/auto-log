package com.github.houbb.auto.log.core.support.interceptor.traceid;

import com.github.houbb.auto.log.annotation.TraceId;
import com.github.houbb.auto.log.api.ITraceIdInterceptorContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author binbin.hou
 * @since 0.0.10
 */
public class TraceIdInterceptorContext implements ITraceIdInterceptorContext {

    /**
     * 注解信息
     * @since 0.0.10
     */
    private TraceId traceId;

    /**
     * 数据集合
     * @since 0.0.10
     */
    private Map<String, Object> dataMap = new HashMap<>();

    public static TraceIdInterceptorContext newInstance() {
        return new TraceIdInterceptorContext();
    }

    public TraceIdInterceptorContext traceId(TraceId traceId) {
        this.traceId = traceId;
        return this;
    }

    @Override
    public TraceId traceId() {
        return this.traceId;
    }

    @Override
    public void put(String key, Object value) {
        this.dataMap.put(key, value);
    }

    @Override
    public Object get(String key) {
        return dataMap.get(key);
    }

}
