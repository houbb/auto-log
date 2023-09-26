package com.github.houbb.auto.log.core.support.interceptor.dubbo;

import com.github.houbb.auto.log.core.core.impl.AbstractAutoLogContext;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;

/**
 * 通用的调用上下文
 *
 * @author d
 * @since 1.0.0
 */
public class AutoLogDubboContext extends AbstractAutoLogContext {

    private Invoker<?> invoker;

    private Invocation invocation;

    public Invoker<?> getInvoker() {
        return invoker;
    }

    public void setInvoker(Invoker<?> invoker) {
        this.invoker = invoker;
    }

    public Invocation getInvocation() {
        return invocation;
    }

    public void setInvocation(Invocation invocation) {
        this.invocation = invocation;
    }

    @Override
    public Object process() throws Throwable {
        return invoker.invoke(invocation);
    }

}
