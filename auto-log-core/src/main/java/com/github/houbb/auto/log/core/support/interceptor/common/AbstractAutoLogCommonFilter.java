package com.github.houbb.auto.log.core.support.interceptor.common;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.core.bs.AutoLogBs;
import com.github.houbb.auto.log.core.exception.AutoLogException;
import com.github.houbb.common.filter.api.CommonFilter;
import com.github.houbb.common.filter.api.Invocation;
import com.github.houbb.common.filter.api.Invoker;
import com.github.houbb.common.filter.api.Result;
import com.github.houbb.common.filter.exception.CommonFilterException;
import com.github.houbb.heaven.annotation.CommonEager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * 通用的抽象实现
 *
 * @since 1.0.0
 */
public class AbstractAutoLogCommonFilter implements CommonFilter {

    protected String buildMethodName(Invoker invoker,
                                     Invocation invocation) {
        return "";
    }

    protected AutoLog buildAutoLog(Invoker invoker,
                                   Invocation invocation) {
        AutoLog autoLog = AutoLogCommonGlobalAnnotation.class.getAnnotation(AutoLog.class);
        String methodName = buildMethodName(invoker, invocation);
        updateAnnotationValue(autoLog, "description", methodName);

        return autoLog;
    }

    @Override
    public Result invoke(Invoker invoker,
                         Invocation invocation)
            throws CommonFilterException {
        AutoLogCommonFilterContext context = new AutoLogCommonFilterContext();
        context.setInvocation(invocation);
        context.setInvoker(invoker);

        AutoLog autoLog = buildAutoLog(invoker, invocation);

        context.autoLog(autoLog);

        try {
            return (Result) AutoLogBs.newInstance()
                    .context(context)
                    .execute();
        } catch (Throwable throwable) {
            throw new AutoLogException(throwable);
        }
    }

    /**
     * 更新注解的值
     * @param annotation 注解
     * @param annotationKey 键
     * @param value 值
     */
    @CommonEager
    protected void updateAnnotationValue(Annotation annotation,
                                         String annotationKey,
                                         Object value) {
        if(annotation != null) {
            try {
                InvocationHandler h = Proxy.getInvocationHandler(annotation);
                Field hField = h.getClass().getDeclaredField("memberValues");
                hField.setAccessible(true);
                Map memberMethods = (Map) hField.get(h);
                memberMethods.put(annotationKey, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
