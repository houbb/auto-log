package com.github.houbb.auto.log.core.support.interceptor.traceid;

import com.github.houbb.auto.log.annotation.TraceId;
import com.github.houbb.auto.log.api.ITraceIdInterceptor;
import com.github.houbb.auto.log.api.ITraceIdInterceptorContext;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.id.api.Id;
import com.github.houbb.id.core.core.Ids;

/**
 * @author binbin.hou
 * @since 0.0.10
 */
public abstract class AbstractTraceIdInterceptor implements ITraceIdInterceptor {

    /**
     * 新建对象实例
     * @param idClass id 类
     * @return 实现
     * @since 0.0.10
     */
    protected Id getIdInstance(final Class<? extends Id> idClass) {
        if(Id.class.equals(idClass)) {
            return Ids.uuid32();
        }
        return ClassUtil.newInstance(idClass);
    }

    /**
     * 是否需要处理日志自动输出
     * @param interceptorContext 上下文
     * @return 结果
     * @since 0.0.10
     */
    protected boolean enableTraceId(ITraceIdInterceptorContext interceptorContext) {
        TraceId traceId = interceptorContext.traceId();
        if(traceId == null) {
            return false;
        }

        return traceId.enable();
    }

    /**
     * 方法执行以前
     * @param traceId 注解
     * @param context 上下文
     * @since 0.0.10
     */
    protected abstract void doBefore(TraceId traceId, ITraceIdInterceptorContext context);

    /**
     * 方法执行以后
     * @param traceId 注解
     * @param result 方法执行结果
     * @param context 上下文
     * @since 0.0.10
     */
    protected void doAfter(TraceId traceId, final Object result,
                                    ITraceIdInterceptorContext context) {

    }

    /**
     * 异常处理
     * @param traceId 注解
     * @param exception 异常信息
     * @param context 上下文
     * @since 0.0.10
     */
    protected void doException(TraceId traceId, Exception exception, ITraceIdInterceptorContext context) {
    }

    /**
     * 最后处理
     * @param traceId 注解
     * @param context 上下文
     * @since 0.0.10
     */
    protected abstract void doFinally(TraceId traceId, ITraceIdInterceptorContext context);

    @Override
    public void beforeHandle(ITraceIdInterceptorContext interceptorContext) {
        if(!enableTraceId(interceptorContext)) {
            return;
        }

        // 执行
        this.doBefore(interceptorContext.traceId(), interceptorContext);
    }

    @Override
    public void finallyHandle(ITraceIdInterceptorContext interceptorContext) {
        if(!enableTraceId(interceptorContext)) {
            return;
        }

        this.doFinally(interceptorContext.traceId(), interceptorContext);
    }

}
