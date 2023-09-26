package com.github.houbb.auto.log.core.support.interceptor.dubbo;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.core.bs.AutoLogBs;
import com.github.houbb.auto.log.core.support.interceptor.common.AutoLogCommonGlobalAnnotation;
import com.github.houbb.heaven.annotation.CommonEager;
import org.apache.dubbo.rpc.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * 通用的抽象实现
 *
 * @since 1.0.0
 */
public abstract class AbstractAutoLogDubboFilter implements Filter {

    protected String buildMethodName(Invoker<?> invoker, Invocation invocation) {
        return invocation.getTargetServiceUniqueName() + "#" + invocation.getMethodName();
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        AutoLogDubboContext context = new AutoLogDubboContext();
        context.setInvoker(invoker);
        context.setInvocation(invocation);
        context.params(invocation.getArguments());

        // 其他属性
        AutoLog autoLog = AutoLogCommonGlobalAnnotation.class.getAnnotation(AutoLog.class);
        String methodName = buildMethodName(invoker, invocation);
        updateAnnotationValue(autoLog, "description", methodName);
        context.autoLog(autoLog);
        //Method 可以暂时不处理

        try {
            return (Result) AutoLogBs.newInstance()
                    .context(context)
                    .execute();
        } catch (Throwable e) {
            throw new RpcException(e);
        }
    }

    /**
     * 更新注解的值
     * @param annotation 注解
     * @param annotationKey 键
     * @param value 值
     */
    @CommonEager
    private void updateAnnotationValue(Annotation annotation,
                                       String annotationKey,
                                       Object value) {
        if(annotation != null) {
            try {
                InvocationHandler h = Proxy.getInvocationHandler(annotation);
                Field hField = h.getClass().getDeclaredField("memberValues");
                hField.setAccessible(true);
                Map memberMethods = (Map) hField.get(h);
                memberMethods.put(annotationKey, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
