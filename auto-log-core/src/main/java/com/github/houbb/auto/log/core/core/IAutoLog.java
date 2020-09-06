package com.github.houbb.auto.log.core.core;

/**
 * @author binbin.hou
 * @since 0.0.7
 */
public interface IAutoLog {

    /**
     * 自动日志输出
     * @param context 上下文
     * @return 结果
     * @since 0.0.7
     */
    Object autoLog(IAutoLogContext context) throws Throwable;

}
