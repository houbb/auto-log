package com.github.houbb.auto.log.api;

/**
 * 参数过滤器接口
 * @author binbin.hou
 * @since 0.0.12
 */
public interface IParamFilter {

    /**
     * 入参过滤接口
     * @param context 上下文
     * @return 结果
     * @since 0.0.12
     */
    Object[] filter(IParamFilterContext context);

}
