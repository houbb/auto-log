package com.github.houbb.auto.log.api;

/**
 * {@link com.github.houbb.auto.log.annotation.TraceId} 拦截器
 * @author binbin.hou
 * @since 0.0.10
 */
public interface ITraceIdInterceptor {

    /**
     * 执行之前
     * @param interceptorContext 拦截器上下文
     * @since 0.0.10
     */
    void beforeHandle(ITraceIdInterceptorContext interceptorContext);

    /**
     * finally 中执行的代码
     * @param interceptorContext 拦截器上下文
     * @since 0.0.10
     */
    void finallyHandle(ITraceIdInterceptorContext interceptorContext);

}
