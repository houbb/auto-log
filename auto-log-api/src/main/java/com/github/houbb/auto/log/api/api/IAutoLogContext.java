package com.github.houbb.auto.log.api.api;

/**
 * 接口设计
 * @author binbin.hou
 * @since 0.0.1
 */
public interface IAutoLogContext {

    /**
     * 原始类
     * @since 0.0.1
     * @return 原始类r
     */
    Object target();

    /**
     * 代理类
     * @return 代理类
     * @since 0.0.1
     */
    Object proxy();

}
