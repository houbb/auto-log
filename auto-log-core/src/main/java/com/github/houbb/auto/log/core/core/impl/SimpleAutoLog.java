package com.github.houbb.auto.log.core.core.impl;

import com.github.houbb.auto.log.api.IAutoLog;
import com.github.houbb.auto.log.api.IAutoLogConfig;
import com.github.houbb.auto.log.api.IAutoLogContext;
import com.github.houbb.auto.log.core.core.AutoLogConfig;
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
        invocation.setFilterParams(context.params());
        invocation.setStartTime(startTimeMills);

        Invoker chainInvoker = InvokerChainBuilder.buildInvokerChain(autoLogInvoker, AutoLogFilterChainConst.GROUP);
        Result autoLogResult = chainInvoker.invoke(invocation);

        //3. 返回结果
        return autoLogResult.getValue();
    }

    private Object[] getFilterParams(IAutoLogContext context) throws Exception {
        Object[] params = context.params();

        if(ArrayUtil.isEmpty(params)) {
            return params;
        }

        Object[] filters = new Object[params.length];
        for(int i = 0; i < params.length; i++) {
            Object param = params[i];

            IAutoLogConfig autoLogConfig = new AutoLogConfig();
            //TODO: 添加实现
            Object filterParam = context.autoLogObjectHandler().handle(param, autoLogConfig);

            filters[i] = filterParam;
        }

        return filters;
    }


}
