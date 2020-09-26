package com.github.houbb.auto.log.api;

/**
 * 入参过滤器上下文
 * @author binbin.hou
 * @since 0.0.12
 */
public interface IParamFilterContext {

    /**
     * 入参信息
     * @return 入参
     * @since 0.0.12
     */
    Object[] params();

}
