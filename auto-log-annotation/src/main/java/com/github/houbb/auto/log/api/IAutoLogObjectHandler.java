package com.github.houbb.auto.log.api;

/**
 * 对象处理器
 * @author binbin.hou
 * @since 0.10.0
 */
public interface IAutoLogObjectHandler {

    /**
     * 处理对象
     * @param rawObject 原始对象
     * @param context 上下文
     * @return 结果
     * @throws Exception 异常信息
     */
    Object handle(Object rawObject, IAutoLogConfig context) throws Exception;

}
