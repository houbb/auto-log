package com.github.houbb.auto.log.core.core.impl;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.api.IAutoLogContext;
import com.github.houbb.auto.log.api.IAutoLogObjectHandler;

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

    /**
     * 对象处理类
     */
    private IAutoLogObjectHandler autoLogObjectHandler;

    /**
     * 丢弃的大小限制
     */
    private int discardSizeLimit;

    /**
     * 最长的日志长度
     * @since 0.11.0
     */
    private int maxLogLen = 65535;

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

    @Override
    public IAutoLogObjectHandler autoLogObjectHandler() {
        return autoLogObjectHandler;
    }

    @Override
    public void autoLogObjectHandler(IAutoLogObjectHandler autoLogObjectHandler) {
        this.autoLogObjectHandler = autoLogObjectHandler;
    }

    @Override
    public int discardSizeLimit() {
        return discardSizeLimit;
    }

    public void discardSizeLimit(int discardSizeLimit) {
        this.discardSizeLimit = discardSizeLimit;
    }

    @Override
    public int maxLogLen() {
        return maxLogLen;
    }

    @Override
    public void maxLogLen(int maxLogLen) {
        this.maxLogLen = maxLogLen;
    }

}
