package com.github.houbb.auto.log.core.support.interceptor.autolog;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.api.IAutoLogInterceptorContext;

import java.lang.reflect.Method;

/**
 * 拦截器上下文
 * @author binbin.hou
 * @since 0.0.10
 */
public class AutoLogInterceptorContext implements IAutoLogInterceptorContext {

    private AutoLog autoLog;

    private Object[] params;

    private Method method;

    private long startTime;

    private long endTime;

    public static AutoLogInterceptorContext newInstance() {
        return new AutoLogInterceptorContext();
    }

    @Override
    public AutoLog autoLog() {
        return autoLog;
    }

    public AutoLogInterceptorContext autoLog(AutoLog autoLog) {
        this.autoLog = autoLog;
        return this;
    }

    @Override
    public Object[] params() {
        return params;
    }

    public AutoLogInterceptorContext params(Object[] params) {
        this.params = params;
        return this;
    }

    @Override
    public Method method() {
        return method;
    }

    public AutoLogInterceptorContext method(Method method) {
        this.method = method;
        return this;
    }

    @Override
    public long startTime() {
        return startTime;
    }

    public AutoLogInterceptorContext startTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    @Override
    public long endTime() {
        return endTime;
    }

    public AutoLogInterceptorContext endTime(long endTime) {
        this.endTime = endTime;
        return this;
    }
}
