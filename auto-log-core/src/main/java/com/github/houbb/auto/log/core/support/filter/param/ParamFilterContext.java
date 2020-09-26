package com.github.houbb.auto.log.core.support.filter.param;

import com.github.houbb.auto.log.api.IParamFilterContext;

/**
 * 上下文信息
 * @author binbin.hou
 * @since 0.0.12
 */
public class ParamFilterContext implements IParamFilterContext {

    /**
     * 参数信息
     * @since 0.0.12
     */
    private Object[] params;

    public static ParamFilterContext newInstance() {
        return new ParamFilterContext();
    }

    @Override
    public Object[] params() {
        return params;
    }

    public ParamFilterContext params(Object[] params) {
        this.params = params;
        return this;
    }

}
