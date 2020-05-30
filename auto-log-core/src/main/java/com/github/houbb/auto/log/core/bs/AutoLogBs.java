package com.github.houbb.auto.log.core.bs;

import com.github.houbb.auto.log.core.support.proxy.CglibProxy;
import com.github.houbb.auto.log.core.support.proxy.DynamicProxy;
import com.github.houbb.heaven.constant.enums.ProxyTypeEnum;
import com.github.houbb.heaven.support.proxy.ProxyFactory;
import com.github.houbb.heaven.support.proxy.none.NoneProxy;

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
     * @return 对应的代理对象
     * @since 0.0.2
     */
    @SuppressWarnings("unchecked")
    public <R> R proxy(final R object) {
        final ProxyTypeEnum proxyTypeEnum = ProxyFactory.getProxyType(object);
        if (ProxyTypeEnum.NONE.equals(proxyTypeEnum)) {
            return (R) new NoneProxy(object).proxy();
        } else if (ProxyTypeEnum.DYNAMIC.equals(proxyTypeEnum)) {
            return (R) new DynamicProxy(object).proxy();
        } else {
            return (R) new CglibProxy(object).proxy();
        }
    }

}
