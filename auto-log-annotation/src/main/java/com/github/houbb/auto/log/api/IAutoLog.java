package com.github.houbb.auto.log.api;

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
     * @throws Throwable 异常信息
     */
    Object autoLog(IAutoLogContext context) throws Throwable;

}
