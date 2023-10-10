package com.github.houbb.auto.log.core.core.impl;

import com.github.houbb.auto.log.api.IAutoLog;
import com.github.houbb.auto.log.api.IAutoLogContext;
import com.github.houbb.auto.log.api.IAutoLogObjectHandler;
import com.github.houbb.auto.log.core.support.interceptor.chain.AutoLogFilterChainConst;
import com.github.houbb.auto.log.core.support.interceptor.chain.AutoLogInvocation;
import com.github.houbb.auto.log.core.support.interceptor.chain.AutoLogInvoker;
import com.github.houbb.common.filter.api.Invoker;
import com.github.houbb.common.filter.api.Result;
import com.github.houbb.common.filter.support.invoke.InvokerChainBuilder;
import com.github.houbb.heaven.util.util.ArrayUtil;

/**
 * @author binbin.hou
 * @since 0.0.7
 */
public class SimpleAutoLog implements IAutoLog {

    /**
     * 自动日志输出
     *
     * @param context 上下文
     * @return 结果
     * @since 0.0.7
     */
    @Override
    public Object autoLog(IAutoLogContext context) throws Throwable {
        //1. 基本信息
        final long startTimeMills = System.currentTimeMillis();

        //2. 调用链设置
        AutoLogInvoker autoLogInvoker = new AutoLogInvoker(context);
        AutoLogInvocation invocation = new AutoLogInvocation();
        invocation.setAutoLogContext(context);
        invocation.setRawParams(context.params());
        invocation.setStartTime(startTimeMills);

        Invoker chainInvoker = InvokerChainBuilder.buildInvokerChain(autoLogInvoker, AutoLogFilterChainConst.GROUP);
        Result autoLogResult = chainInvoker.invoke(invocation);

        //3. 返回结果
        return autoLogResult.getValue();
    }

}
