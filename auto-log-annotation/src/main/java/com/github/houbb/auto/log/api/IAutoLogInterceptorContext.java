package com.github.houbb.auto.log.api;

import com.github.houbb.auto.log.annotation.AutoLog;

import java.lang.reflect.Method;

/**
 * {@link AutoLog} 拦截器上下文
 * @author binbin.hou
 * @since 0.0.10
 */
public interface IAutoLogInterceptorContext {

    /**
     * 注解信息
     * @return 注解信息
     * @since 0.0.10
     */
    AutoLog autoLog();

    /**
     * 参数信息
     * @return 参数信息
     * @since 0.0.10
     */
    Object[] params();

    /**
     * 方法信息
     * @return 方法信息
     * @since 0.0.10
     */
    Method method();

    /**
     * 开始时间
     * @return 开始时间
     * @since 0.0.10
     */
    long startTime();

    /**
     * 结束时间
     * @return 时间
     * @since 0.0.10
     */
    long endTime();

}
