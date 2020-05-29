package com.github.houbb.auto.log.core.support.proxy;

import com.github.houbb.auto.log.core.support.interceptor.AutoLogMethodInterceptor;
import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.heaven.support.proxy.IProxy;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.*;

/**
 * 动态代理
 *
 * @author binbin.hou
 * @since 0.0.2
 */
@ThreadSafe
public class DynamicProxy implements InvocationHandler, IProxy {

    /**
     * 目标对象
     * @since 0.0.2
     */
    private final Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        try {
            //1. 构建对象
            MethodInvocation methodInvocation = new MethodInvocation() {
                @Override
                public Method getMethod() {
                    return method;
                }

                @Override
                public Object[] getArguments() {
                    return args;
                }

                @Override
                public Object proceed() throws Throwable {
                    // 反射执行
                    return method.invoke(target, args);
                }

                @Override
                public Object getThis() {
                    return target;
                }

                @Override
                public AccessibleObject getStaticPart() {
                    return null;
                }
            };

            return Instances.singleton(AutoLogMethodInterceptor.class)
                    .invoke(methodInvocation);
        } catch (InvocationTargetException ex) {
            // 程序内部没有处理的异常
            throw ex.getTargetException();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Object proxy() {
        // 我们要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
        InvocationHandler handler = new DynamicProxy(target);

        return Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                target.getClass().getInterfaces(), handler);
    }

}
