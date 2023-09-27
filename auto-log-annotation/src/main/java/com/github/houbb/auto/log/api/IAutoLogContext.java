package com.github.houbb.auto.log.api;

import com.github.houbb.auto.log.annotation.AutoLog;

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

    /**
     * 设置处理类
     * @param autoLogObjectHandler 处理类
     */
    void autoLogObjectHandler(IAutoLogObjectHandler autoLogObjectHandler);

    /**
     * 获取处理类
     * @return 获取
     */
    IAutoLogObjectHandler autoLogObjectHandler();

    /**
     * 丢弃的大小限制
     */
    int discardSizeLimit();

    /**
     * 设置大小
     * @param discardSizeLimit 限制
     */
    void discardSizeLimit(int discardSizeLimit);

}
