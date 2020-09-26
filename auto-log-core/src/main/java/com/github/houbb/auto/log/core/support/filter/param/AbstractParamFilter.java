package com.github.houbb.auto.log.core.support.filter.param;

import com.github.houbb.auto.log.api.IParamFilter;
import com.github.houbb.auto.log.api.IParamFilterContext;
import com.github.houbb.heaven.util.util.ArrayUtil;

/**
 * 抽象参数过滤器实现
 * @author binbin.hou
 * @since 0.0.12
 */
public abstract class AbstractParamFilter implements IParamFilter {

    /**
     * 执行参数过滤
     * @param params 入参
     * @return 结果
     * @since 0.0.12
     */
    protected abstract Object[] doFilter(Object[] params);

    @Override
    public Object[] filter(IParamFilterContext context) {
        Object[] params = context.params();
        return doFilter(params);
    }

}
