package com.github.houbb.auto.log.core.support.proxy;

import com.github.houbb.auto.log.core.support.interceptor.AutoLogMethodInterceptor;
import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.heaven.support.proxy.IProxy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 动态代理
 *
 * @author binbin.hou
 * @since 0.0.2
 */
@ThreadSafe
public class CglibProxy implements IProxy, MethodInterceptor {

    /**
     * 目标对象
     * @since 0.0.2
     */
    private final Object target;

    public CglibProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(final Object o, final Method method,
                            final Object[] objects, final MethodProxy methodProxy) throws Throwable {
        try {
            MethodInvocation methodInvocation = new MethodInvocation() {
                @Override
                public Method getMethod() {
                    return method;
                }

                @Override
                public Object[] getArguments() {
                    return objects;
                }

                @Override
                public Object proceed() throws Throwable {
                    return method.invoke(target, objects);
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
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    @Override
    public Object proxy() {
        Enhancer enhancer = new Enhancer();
        //目标对象类
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        //通过字节码技术创建目标对象类的子类实例作为代理
        return enhancer.create();
    }

}
