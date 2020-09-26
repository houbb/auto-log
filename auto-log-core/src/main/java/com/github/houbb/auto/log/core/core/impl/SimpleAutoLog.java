package com.github.houbb.auto.log.core.core.impl;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.annotation.TraceId;
import com.github.houbb.auto.log.api.*;
import com.github.houbb.auto.log.core.support.filter.param.ParamFilterContext;
import com.github.houbb.auto.log.core.support.filter.param.WebParamFilter;
import com.github.houbb.auto.log.core.support.interceptor.autolog.AutoLogInterceptor;
import com.github.houbb.auto.log.core.support.interceptor.autolog.AutoLogInterceptorContext;
import com.github.houbb.auto.log.core.support.interceptor.traceid.TraceIdInterceptor;
import com.github.houbb.auto.log.core.support.interceptor.traceid.TraceIdInterceptorContext;
import com.github.houbb.auto.log.exception.AutoLogRuntimeException;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

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
        //1. 日志唯一标识
        final long startTimeMills = System.currentTimeMillis();
        TraceId traceId = context.traceId();
        AutoLog autoLog = context.autoLog();
        ITraceIdInterceptorContext traceIdContext = null;
        AutoLogInterceptorContext autoLogContext = null;
        List<ITraceIdInterceptor> traceIdInterceptors = null;
        List<IAutoLogInterceptor> autoLogInterceptors = null;

        try {
            // 统一对象处理
            // 执行入参过滤
            Object[] params = context.params();
            Object[] filterParams = params;
            Class<? extends IParamFilter> paramFilterClass = autoLog.paramFilter();
            if(IParamFilter.class.equals(paramFilterClass)) {
                // 默认策略
                paramFilterClass = WebParamFilter.class;
            }
            IParamFilter paramFilter = ClassUtil.newInstance(paramFilterClass);
            IParamFilterContext filterContext = ParamFilterContext.newInstance().params(params);
            filterParams = paramFilter.filter(filterContext);

            traceIdContext = TraceIdInterceptorContext.newInstance().traceId(traceId);
            traceIdInterceptors = traceIdInterceptors(traceId);
            autoLogContext = AutoLogInterceptorContext.newInstance().autoLog(autoLog).startTime(startTimeMills)
                    .params(params)
                    .filterParams(filterParams)
                    .method(context.method());
            autoLogInterceptors = autoLogInterceptors(autoLog);

            //1. 执行前
            //1.1 traceId
            if(CollectionUtil.isNotEmpty(traceIdInterceptors)) {
                for(ITraceIdInterceptor interceptor : traceIdInterceptors) {
                    // 执行
                    interceptor.beforeHandle(traceIdContext);
                }
            }

            //1.2 autoLog
            if(CollectionUtil.isNotEmpty(autoLogInterceptors)) {
                for(IAutoLogInterceptor interceptor : autoLogInterceptors) {
                    interceptor.beforeHandle(autoLogContext);
                }
            }

            //2. 执行结果
            Object result = context.process();
            final long endTimeMills = System.currentTimeMillis();
            autoLogContext.endTime(endTimeMills);

            //2.1 方法执行后
            if(CollectionUtil.isNotEmpty(autoLogInterceptors)) {
                for(IAutoLogInterceptor interceptor : autoLogInterceptors) {
                    interceptor.afterHandle(autoLogContext, result);
                }
            }

            //2.2 返回方法
            return result;
        } catch (Exception exception) {
            if(CollectionUtil.isNotEmpty(autoLogInterceptors)) {
                for(IAutoLogInterceptor interceptor : autoLogInterceptors) {
                    interceptor.exceptionHandle(autoLogContext, exception);
                }
            }

            throw new AutoLogRuntimeException(exception);
        } finally {
            // 先执行日志
            if(CollectionUtil.isNotEmpty(autoLogInterceptors)) {
                for(IAutoLogInterceptor interceptor : autoLogInterceptors) {
                    interceptor.finallyHandle(autoLogContext);
                }
            }

            // 后移除
            if(CollectionUtil.isNotEmpty(traceIdInterceptors)) {
                for(ITraceIdInterceptor interceptor : traceIdInterceptors) {
                    // 执行
                    interceptor.finallyHandle(traceIdContext);
                }
            }
        }
    }

    /**
     * 创建拦截器列表
     * @param autoLog 注解
     * @return 结果
     * @since 0.0.10
     */
    private List<IAutoLogInterceptor> autoLogInterceptors(final AutoLog autoLog) {
        List<IAutoLogInterceptor> resultList = new ArrayList<>();
        if(ObjectUtil.isNull(autoLog)) {
            return resultList;
        }

        Class<? extends IAutoLogInterceptor>[] interceptorClasses = autoLog.interceptor();
        if(ArrayUtil.isEmpty(interceptorClasses)) {
            return resultList;
        }

        // 循环创建
        for(Class<? extends IAutoLogInterceptor> clazz : interceptorClasses) {
            IAutoLogInterceptor traceIdInterceptor = createAutoLogInterceptor(clazz);
            resultList.add(traceIdInterceptor);
        }

        return resultList;
    }

    /**
     * 创建拦截器列表
     * @param traceId 注解
     * @return 结果
     * @since 0.0.10
     */
    private List<ITraceIdInterceptor> traceIdInterceptors(final TraceId traceId) {
        List<ITraceIdInterceptor> resultList = new ArrayList<>();
        if(ObjectUtil.isNull(traceId)) {
            return resultList;
        }

        Class<? extends ITraceIdInterceptor>[] interceptorClasses = traceId.interceptor();
        if(ArrayUtil.isEmpty(interceptorClasses)) {
            return resultList;
        }

        // 循环创建
        for(Class<? extends ITraceIdInterceptor> clazz : interceptorClasses) {
            ITraceIdInterceptor traceIdInterceptor = createTraceIdInterceptor(clazz);
            resultList.add(traceIdInterceptor);
        }

        return resultList;
    }

    /**
     * 创建拦截器
     * @param clazz 类
     * @return 实体
     * @since 0.0.10
     */
    private ITraceIdInterceptor createTraceIdInterceptor(final Class<? extends ITraceIdInterceptor> clazz) {
        if(ITraceIdInterceptor.class.equals(clazz)) {
            return new TraceIdInterceptor();
        }

        return ClassUtil.newInstance(clazz);
    }

    /**
     * 创建拦截器
     * @param clazz 类
     * @return 实体
     * @since 0.0.10
     */
    private IAutoLogInterceptor createAutoLogInterceptor(final Class<? extends IAutoLogInterceptor> clazz) {
        if(IAutoLogInterceptor.class.equals(clazz)) {
            return new AutoLogInterceptor();
        }

        return ClassUtil.newInstance(clazz);
    }

}
