package com.github.houbb.auto.log.core.support.interceptor;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 1. 格式化可以自定义
 * @author binbin.hou
 * @since 0.0.2
 */
@ThreadSafe
public class AutoLogMethodInterceptor implements MethodInterceptor {

    private static final Log LOG = LogFactory.getLog(AutoLogMethodInterceptor.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        final long startMills = System.currentTimeMillis();

        Method method = methodInvocation.getMethod();
        String methodName = method.toString();
        AutoLog autoLog = method.getAnnotation(AutoLog.class);
        if(ObjectUtil.isNotNull(autoLog)) {
            //1. 是否输入入参
            if(autoLog.param()) {
                Object[] params = methodInvocation.getArguments();
                LOG.info("{} param is {}", methodName, Arrays.toString(params));
            }
        }

        //2. 执行
        Object result = methodInvocation.proceed();
        if(ObjectUtil.isNull(autoLog)) {
            return result;
        }

        //3. 结果
        if(autoLog.result()) {
            LOG.info("{} result is {}", methodName, result);
        }
        //3.1 耗时
        if(autoLog.costTime()) {
            final long endMills = System.currentTimeMillis();
            long costTime = endMills-startMills;
            LOG.info("{} cost time is {}ms", methodName, costTime);
        }

        return result;
    }

}
