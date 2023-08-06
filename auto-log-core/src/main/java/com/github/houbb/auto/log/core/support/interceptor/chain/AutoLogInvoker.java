package com.github.houbb.auto.log.core.support.interceptor.chain;

import com.github.houbb.auto.log.api.IAutoLogContext;
import com.github.houbb.common.filter.api.Invocation;
import com.github.houbb.common.filter.api.Invoker;
import com.github.houbb.common.filter.api.Result;
import com.github.houbb.common.filter.exception.CommonFilterException;
import com.github.houbb.common.filter.support.result.CommonResult;

/**
 * 默认的 invoker
 *
 * @since 0.4.0
 */
public class AutoLogInvoker implements Invoker {

    private final IAutoLogContext context;

    public AutoLogInvoker(IAutoLogContext context) {
        this.context = context;
    }

    @Override
    public Result invoke(Invocation invocation) throws CommonFilterException {
        Result result = new CommonResult();
        try {
            Object resultValue = context.process();
            result.setValue(resultValue);
        } catch (Throwable e) {
            result.setException(e);
            throw new RuntimeException(e);
        }

        return result;
    }

}
