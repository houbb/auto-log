package com.github.houbb.auto.log.core.core.impl;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.api.IAutoLog;
import com.github.houbb.auto.log.api.IAutoLogContext;
import com.github.houbb.auto.log.api.IParamFilter;
import com.github.houbb.auto.log.api.IParamFilterContext;
import com.github.houbb.auto.log.core.support.filter.param.ParamFilterContext;
import com.github.houbb.auto.log.core.support.filter.param.WebParamFilter;
import com.github.houbb.auto.log.core.support.interceptor.chain.AutoLogInvocation;
import com.github.houbb.auto.log.core.support.interceptor.chain.AutoLogInvoker;
import com.github.houbb.common.filter.api.Invoker;
import com.github.houbb.common.filter.api.Result;
import com.github.houbb.common.filter.support.invoke.InvokerChainBuilder;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;

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
        Object[] filterParams = getFilterParams(context);

        //2. 调用链设置
        AutoLogInvoker autoLogInvoker = new AutoLogInvoker(context);
        AutoLogInvocation invocation = new AutoLogInvocation();
        invocation.setAutoLogContext(context);
        invocation.setFilterParams(filterParams);
        invocation.setStartTime(startTimeMills);

        Invoker chainInvoker = InvokerChainBuilder.buildInvokerChain(autoLogInvoker);
        Result autoLogResult = chainInvoker.invoke(invocation);

        //3. 返回结果
        return autoLogResult.getValue();
    }

    /**
     * 统一的参数过滤，甚至可以考虑改成 filter chain
     *
     * @param context 上下文
     * @return 结果
     * @since 0.4.0
     */
    private Object[] getFilterParams(IAutoLogContext context) {
        final AutoLog autoLog = context.autoLog();

        // 统一对象处理
        // 执行入参过滤
        Object[] params = context.params();
        Class<? extends IParamFilter> paramFilterClass = autoLog.paramFilter();
        if (IParamFilter.class.equals(paramFilterClass)) {
            // 默认策略
            paramFilterClass = WebParamFilter.class;
        }
        IParamFilter paramFilter = ClassUtil.newInstance(paramFilterClass);
        IParamFilterContext filterContext = ParamFilterContext.newInstance().params(params);
        return paramFilter.filter(filterContext);
    }

}
