package com.github.houbb.auto.log.core.core;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.annotation.TraceId;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自动日志输出上下文
 * @author binbin.hou
 * @since 0.0.7
 */
public interface IAutoLogContext {

    /**
     * 注解信息
     * @return 注解信息
     * @since 0.0.7
     */
    AutoLog autoLog();

    /**
     * 设置日志唯一标识的注解
     * @return 日志唯一标识
     * @since 0.0.8
     */
    TraceId traceId();

    /**
     * 参数信息
     * @return 参数信息
     * @since 0.0.7
     */
    Object[] params();

    /**
     * 方法信息
     * @return 方法信息
     * @since 0.0.7
     */
    Method method();

    /**
     * 方法执行
     * @return 执行
     * @since 0.0.7
     * @throws Throwable 异常信息
     */
    Object process() throws Throwable;

}
