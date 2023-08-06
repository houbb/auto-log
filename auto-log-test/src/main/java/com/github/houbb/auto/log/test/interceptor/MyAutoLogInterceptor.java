package com.github.houbb.auto.log.test.interceptor;

import com.github.houbb.auto.log.core.constant.AutoLogAttachmentKeyConst;
import com.github.houbb.common.filter.annotation.FilterActive;
import com.github.houbb.common.filter.api.CommonFilter;
import com.github.houbb.common.filter.api.Invocation;
import com.github.houbb.common.filter.api.Invoker;
import com.github.houbb.common.filter.api.Result;
import com.github.houbb.common.filter.exception.CommonFilterException;

/**
 * 自定义日志拦截器
 * @author binbin.hou
 * @since 0.0.12
 */
@FilterActive(order = 1)
public class MyAutoLogInterceptor implements CommonFilter {

    @Override
    public Result invoke(Invoker invoker, Invocation invocation) throws CommonFilterException {
        final String tid = (String) invocation.getAttachment(AutoLogAttachmentKeyConst.AUTO_LOG_TRACE_ID);
        System.out.println("my test filter before " + tid);
        Result result = invoker.invoke(invocation);
        System.out.println("my test filter after " + tid);

        return result;
    }

}
