package com.github.houbb.auto.log.core.bs;

import com.github.houbb.aop.core.util.ProxyUtil;
import com.github.houbb.auto.log.core.support.interceptor.AutoLogMethodInterceptor;
import com.github.houbb.heaven.support.instance.impl.Instances;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public final class AutoLogBs {

    private AutoLogBs() {
    }

    /**
     * 新建对象实例
     * @return this
     * @since 0.0.3
     */
    public static AutoLogBs newInstance() {
        return new AutoLogBs();
    }

    /**
     * 根据对象获取对应的代理
     *
     * 提取时将方法处理类当做入参
     * @param object 原始对象
     * @param <R> 泛型
     * @return 对应的代理对象
     * @since 0.0.2
     */
    @SuppressWarnings("unchecked")
    public <R> R proxy(final R object) {
        final MethodInterceptor methodInterceptor = Instances.singleton(AutoLogMethodInterceptor.class);
        return ProxyUtil.proxy(object, methodInterceptor);
    }

}
