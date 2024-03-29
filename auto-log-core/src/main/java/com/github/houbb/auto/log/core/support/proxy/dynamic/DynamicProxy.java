/*
 * Copyright (c)  2019. houbinbin Inc.
 * async All rights reserved.
 */

package com.github.houbb.auto.log.core.support.proxy.dynamic;

import com.github.houbb.auto.log.core.bs.AutoLogBs;
import com.github.houbb.auto.log.core.core.impl.SimpleAutoLogContext;
import com.github.houbb.auto.log.core.support.proxy.IAutoLogProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CompletionService;

/**
 * <p> 动态代理 </p>
 * <p>
 * 1. 对于 executor 的抽象，使用 {@link CompletionService}
 * 2. 确保唯一初始化 executor，在任务执行的最后关闭 executor。
 * 3. 异步执行结果的获取，异常信息的获取。
 * <pre> Created: 2019/3/5 10:23 PM  </pre>
 * <pre> Project: async  </pre>
 *
 * @author houbinbin
 * @since 0.0.1
 */
public class DynamicProxy implements InvocationHandler, IAutoLogProxy {

    /**
     * 被代理的对象
     */
    private final Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    /**
     * 这种方式虽然实现了异步执行，但是存在一个缺陷：
     * 强制用户返回值为 Future 的子类。
     * <p>
     * 如何实现不影响原来的值，要怎么实现呢？
     *
     * @param proxy  原始对象
     * @param method 方法
     * @param args   入参
     * @return 结果
     * @throws Throwable 异常
     */
    @Override
    @SuppressWarnings("all")
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        SimpleAutoLogContext context = SimpleAutoLogContext.newInstance();
        context.method(method)
                .params(args)
        ;

        context.target(target);
        return AutoLogBs.newInstance().context(context).execute();
    }

    @Override
    public Object proxy() {
        // 我们要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
        InvocationHandler handler = new DynamicProxy(target);

        return Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                target.getClass().getInterfaces(), handler);
    }
}
