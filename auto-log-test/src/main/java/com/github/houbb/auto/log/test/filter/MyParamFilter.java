package com.github.houbb.auto.log.test.filter;

import com.github.houbb.auto.log.core.support.filter.param.AbstractParamFilter;

/**
 * @author binbin.hou
 * @since 0.0.12
 */
public class MyParamFilter extends AbstractParamFilter {

    @Override
    protected Object[] doFilter(Object[] params) {
        Object[] newParams = new Object[1];
        newParams[0] = "设置我我想要的值";
        return newParams;
    }

}
