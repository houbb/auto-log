package com.github.houbb.auto.log.core.support.dubbo;

import com.github.houbb.auto.log.core.core.impl.AbstractAutoLogContext;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;

/**
 * @since 0.7.0
 */
public class DubboAutoLogContext extends AbstractAutoLogContext {

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
