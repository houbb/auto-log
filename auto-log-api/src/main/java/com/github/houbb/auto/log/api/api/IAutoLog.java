package com.github.houbb.auto.log.api.api;

/**
 * 接口设计
 * @author binbin.hou
 * @since 0.0.1
 */
public interface IAutoLog {

    /**
     * 自动日志输出
     * @param context 上下文
     * @return 结果
     * @since 0.0.1
     */
    Object autoLog(final IAutoLogContext context);

}
