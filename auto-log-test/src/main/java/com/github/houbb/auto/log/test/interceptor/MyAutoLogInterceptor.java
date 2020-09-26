package com.github.houbb.auto.log.test.interceptor;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.api.IAutoLogInterceptor;
import com.github.houbb.auto.log.api.IAutoLogInterceptorContext;
import com.github.houbb.auto.log.core.support.interceptor.autolog.AbstractAutoLogInterceptor;

import java.util.Arrays;

/**
 * 自定义日志拦截器
 * @author binbin.hou
 * @since 0.0.12
 */
public class MyAutoLogInterceptor extends AbstractAutoLogInterceptor {

    @Override
    protected void doBefore(AutoLog autoLog, IAutoLogInterceptorContext context) {
        System.out.println("自定义入参：" + Arrays.toString(context.filterParams()));
    }

    @Override
    protected void doAfter(AutoLog autoLog, Object result, IAutoLogInterceptorContext context) {
        System.out.println("自定义出参：" + result);
    }

    @Override
    protected void doException(AutoLog autoLog, Exception exception, IAutoLogInterceptorContext context) {
        System.out.println("自定义异常：");
        exception.printStackTrace();
    }

}
