package com.github.houbb.auto.log.core.support.interceptor.autolog;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.api.IAutoLogInterceptor;
import com.github.houbb.auto.log.api.IAutoLogInterceptorContext;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.id.api.Id;
import com.github.houbb.id.core.core.Ids;
import com.github.houbb.id.core.util.IdThreadLocalHelper;

import java.lang.reflect.Method;

/**
 * @author binbin.hou
 * @since 0.0.10
 */
public abstract class AbstractAutoLogInterceptor implements IAutoLogInterceptor {

    /**
     * 是否需要处理日志自动输出
     * @param interceptorContext 上下文
     * @return 结果
     * @since 0.0.10
     */
    protected boolean enableAutoLog(IAutoLogInterceptorContext interceptorContext) {
        AutoLog autoLog = interceptorContext.autoLog();
        if(autoLog == null) {
            return false;
        }

        return autoLog.enable();
    }

    /**
     * 获取方法描述
     * @param method 方法
     * @param autoLog 注解
     * @return 结果
     * @since 0.0.10
     */
    protected String getMethodDescription(Method method, AutoLog autoLog) {
        if(autoLog == null) {
            return method.getName();
        }

        String description = autoLog.description();
        if(StringUtil.isNotEmpty(description)) {
            return description;
        }

        return method.getName();
    }

    /**
     * 获取 traceId
     * @param autoLog 日志注解
     * @return 结果
     * @since 0.0.10
     */
    protected String getTraceId(AutoLog autoLog) {
        //1. 优先看当前线程中是否存在
        String oldId = IdThreadLocalHelper.get();
        if(StringUtil.isNotEmpty(oldId)) {
            return formatTraceId(oldId);
        }

        //2. 返回对应的标识
        Id id = getActualTraceId(autoLog);
        return formatTraceId(id.id());
    }

    /**
     * 获取日志跟踪号策略
     * @param autoLog 注解
     * @return 没结果
     */
    protected Id getActualTraceId(AutoLog autoLog) {
        Class<? extends Id> idClass = autoLog.traceId();
        if(Id.class.equals(idClass)) {
            return Ids.uuid32();
        }
        return ClassUtil.newInstance(autoLog.traceId());
    }

    /**
     * 格式化日志跟踪号
     * @param id 跟踪号
     * @return 结果
     * @since 0.0.16
     */
    protected String formatTraceId(String id) {
        return String.format("[%s] ", id);
    }

    /**
     * 方法执行以前
     * @param autoLog 注解
     * @param context 上下文
     * @since 0.0.10
     */
    protected abstract void doBefore(AutoLog autoLog, IAutoLogInterceptorContext context);

    /**
     * 方法执行以后
     * @param autoLog 注解
     * @param result 方法执行结果
     * @param context 上下文
     * @since 0.0.10
     */
    protected abstract void doAfter(AutoLog autoLog, final Object result,
                                    IAutoLogInterceptorContext context);

    /**
     * 异常处理
     * @param autoLog 注解
     * @param exception 异常信息
     * @param context 上下文
     * @since 0.0.10
     */
    protected abstract void doException(AutoLog autoLog, Exception exception, IAutoLogInterceptorContext context);

    /**
     * 最后处理
     * @param autoLog 注解
     * @param context 上下文
     * @since 0.0.10
     */
    protected void doFinally(AutoLog autoLog, IAutoLogInterceptorContext context) {
    }

    @Override
    public void beforeHandle(IAutoLogInterceptorContext interceptorContext) {
        if(!enableAutoLog(interceptorContext)) {
            return;
        }
        // 执行
        this.doBefore(interceptorContext.autoLog(), interceptorContext);
    }

    @Override
    public void afterHandle(IAutoLogInterceptorContext interceptorContext, Object result) {
        if(!enableAutoLog(interceptorContext)) {
            return;
        }

        this.doAfter(interceptorContext.autoLog(), result, interceptorContext);
    }

    @Override
    public void exceptionHandle(IAutoLogInterceptorContext interceptorContext, Exception exception) {
        if(!enableAutoLog(interceptorContext)) {
            return;
        }

        this.doException(interceptorContext.autoLog(), exception, interceptorContext);
    }

    @Override
    public void finallyHandle(IAutoLogInterceptorContext interceptorContext) {
        if(!enableAutoLog(interceptorContext)) {
            return;
        }

        this.doFinally(interceptorContext.autoLog(), interceptorContext);
    }

}
