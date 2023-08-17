package com.github.houbb.auto.log.core.core.impl;

/**
 * 自动日志输出上下文
 * @author binbin.hou
 * @since 0.0.7
 */
public class SimpleAutoLogContext extends AbstractAutoLogContext {

    /**
     * 目标对象
     * @since 0.0.7
     */
    private Object target;

    public static SimpleAutoLogContext newInstance() {
        return new SimpleAutoLogContext();
    }

    public Object target() {
        return target;
    }

    public SimpleAutoLogContext target(Object target) {
        this.target = target;
        return this;
    }

    @Override
    public Object process() throws Exception {
        return super.method().invoke(target, super.params());
    }

}
