package com.github.houbb.auto.log.core.support.interceptor.chain;

import com.github.houbb.auto.log.api.IAutoLogContext;
import com.github.houbb.common.filter.support.invocation.CommonInvocation;

/**
 * 日志 invocation
 * @since 0.7.0
 */
public class AutoLogInvocation extends CommonInvocation {

    private IAutoLogContext autoLogContext;

    private long startTime;

    private Object[] filterParams;

    /**
     * 原始的入参
     */
    private Object[] rawParams;

    public IAutoLogContext getAutoLogContext() {
        return autoLogContext;
    }

    public void setAutoLogContext(IAutoLogContext autoLogContext) {
        this.autoLogContext = autoLogContext;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Object[] getFilterParams() {
        return filterParams;
    }

    public void setFilterParams(Object[] filterParams) {
        this.filterParams = filterParams;
    }

    public Object[] getRawParams() {
        return rawParams;
    }

    public void setRawParams(Object[] rawParams) {
        this.rawParams = rawParams;
    }
}
