package com.github.houbb.auto.log.core.core.impl;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.annotation.TraceId;
import com.github.houbb.auto.log.api.IAutoLogContext;

import java.lang.reflect.Method;

/**
 * 自动日志输出上下文
 * @author binbin.hou
 * @since 0.0.7
 */
public class SimpleAutoLogContext implements IAutoLogContext {

    /**
     * 注解信息
     * @since 0.0.7
     */
    private AutoLog autoLog;

    /**
     * 日志标识注解
     * @since 0.0.8
     */
    private TraceId traceId;

    /**
     * 参数信息
     * @since 0.0.7
     */
    private Object[] params;

    /**
     * 方法信息
     * @since 0.0.7
     */
    private Method method;

    /**
     * 目标对象
     * @since 0.0.7
     */
    private Object target;

    public static SimpleAutoLogContext newInstance() {
        return new SimpleAutoLogContext();
    }

    @Override
    public AutoLog autoLog() {
        return autoLog;
    }

    @Override
    public TraceId traceId() {
        return traceId;
    }

    public Object target() {
        return target;
    }

    public SimpleAutoLogContext target(Object target) {
        this.target = target;
        return this;
    }

    @Override
    public Object[] params() {
        return params;
    }

    public SimpleAutoLogContext params(Object[] params) {
        this.params = params;
        return this;
    }

    @Override
    public Method method() {
        return method;
    }

    public SimpleAutoLogContext method(Method method) {
        this.method = method;
        AutoLog autoLog = method.getAnnotation(AutoLog.class);
        TraceId traceId = method.getAnnotation(TraceId.class);
        Class<?> clazz = method.getDeclaringClass();

        if(autoLog == null) {
            autoLog = clazz.getAnnotation(AutoLog.class);
        }
        if(traceId == null) {
            traceId = clazz.getAnnotation(TraceId.class);
        }

        this.autoLog = autoLog;
        this.traceId = traceId;

        return this;
    }

    @Override
    public Object process() throws Exception {
        return method.invoke(target, params);
    }

}
