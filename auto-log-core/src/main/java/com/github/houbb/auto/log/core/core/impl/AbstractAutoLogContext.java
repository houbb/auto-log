package com.github.houbb.auto.log.core.core.impl;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.api.IAutoLogContext;

import java.lang.reflect.Method;

/**
 * 自动日志输出上下文
 * @author binbin.hou
 * @since 0.3.0
 */
public abstract class AbstractAutoLogContext implements IAutoLogContext {

    /**
     * 注解信息
     * @since 0.0.7
     */
    private AutoLog autoLog;

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

    @Override
    public AutoLog autoLog() {
        return autoLog;
    }

    public AbstractAutoLogContext autoLog(AutoLog autoLog) {
        this.autoLog = autoLog;
        return this;
    }

    @Override
    public Object[] params() {
        return params;
    }

    public AbstractAutoLogContext params(Object[] params) {
        this.params = params;
        return this;
    }

    @Override
    public Method method() {
        return method;
    }

    public AbstractAutoLogContext method(Method method) {
        this.method = method;
        return this;
    }

}
