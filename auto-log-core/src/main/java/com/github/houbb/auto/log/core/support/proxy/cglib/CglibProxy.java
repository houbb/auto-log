package com.github.houbb.auto.log.core.support.proxy.cglib;

import com.github.houbb.auto.log.core.bs.AutoLogBs;
import com.github.houbb.auto.log.core.core.IAutoLogContext;
import com.github.houbb.auto.log.core.core.impl.SimpleAutoLogContext;
import com.github.houbb.auto.log.core.support.proxy.IAutoLogProxy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB 代理类
 * @author binbin.hou
 * date 2019/3/7
 * @since 0.0.2
 */
public class CglibProxy implements MethodInterceptor, IAutoLogProxy {

    /**
     * 被代理的对象
     */
    private final Object target;

    public CglibProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        IAutoLogContext context = SimpleAutoLogContext.newInstance()
                .method(method).params(objects).target(target);
        return AutoLogBs.newInstance().context(context).execute();
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
