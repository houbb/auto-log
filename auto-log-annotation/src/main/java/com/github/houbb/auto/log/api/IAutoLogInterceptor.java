package com.github.houbb.auto.log.api;

/**
 * autoLog 拦截器
 * @author binbin.hou
 * @since 0.0.10
 */
public interface IAutoLogInterceptor {

    /**
     * 执行之前
     * @param interceptorContext 拦截器上下文
     * @since 0.0.10
     */
    void beforeHandle(IAutoLogInterceptorContext interceptorContext);

    /**
     * 执行之后
     * @param interceptorContext 拦截器上下文
     * @param result 方法执行结果
     * @since 0.0.10
     */
    void afterHandle(IAutoLogInterceptorContext interceptorContext,
                     final Object result);

    /**
     * 异常处理
     * @param interceptorContext 拦截器上下文
     * @param exception 异常
     * @since 0.0.10
     */
    void exceptionHandle(IAutoLogInterceptorContext interceptorContext, Exception exception);

    /**
     * finally 中执行的代码
     * @param interceptorContext 拦截器上下文
     * @since 0.0.10
     */
    void finallyHandle(IAutoLogInterceptorContext interceptorContext);

}
