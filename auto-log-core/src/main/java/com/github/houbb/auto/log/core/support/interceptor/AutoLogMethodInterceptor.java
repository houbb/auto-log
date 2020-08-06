package com.github.houbb.auto.log.core.support.interceptor;

import com.github.houbb.aop.core.util.MethodInvocationUtil;
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
 *
 * @author binbin.hou
 * @since 0.0.2
 */
@ThreadSafe
public class AutoLogMethodInterceptor implements MethodInterceptor {

    private static final Log LOG = LogFactory.getLog(AutoLogMethodInterceptor.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        String methodName = null;
        AutoLog autoLog = null;

        try {
            final long startMills = System.currentTimeMillis();
            // 避免 AOP 时获取不到真正的实现
            Method method = MethodInvocationUtil.getActualMethod(methodInvocation);
            methodName = method.toString();
            autoLog = method.getAnnotation(AutoLog.class);

            if (ObjectUtil.isNotNull(autoLog)) {
                //1. 是否输入入参
                if (autoLog.param()) {
                    Object[] params = methodInvocation.getArguments();
                    LOG.info("{} param is {}.", methodName, Arrays.toString(params));
                }
            }

            //2. 执行
            Object result = methodInvocation.proceed();
            if (ObjectUtil.isNull(autoLog)) {
                return result;
            }

            //3. 结果
            if (autoLog.result()) {
                LOG.info("{} result is {}.", methodName, result);
            }
            //3.1 耗时
            final long slowThreshold = autoLog.slowThresholdMills();
            if (autoLog.costTime() || slowThreshold >= 0) {
                final long endMills = System.currentTimeMillis();
                long costTime = endMills - startMills;
                if (autoLog.costTime()) {
                    LOG.info("{} cost time is {}ms.", methodName, costTime);
                }

                //3.2 慢日志
                if (slowThreshold >= 0 && costTime >= slowThreshold) {
                    LOG.warn("{} is slow log, {}ms >= {}ms.", methodName, costTime, slowThreshold);
                }
            }

            return result;
        } catch (Throwable throwable) {
            if (ObjectUtil.isNotNull(autoLog)) {
                LOG.error("{} meet ex.", methodName, throwable);
            }
            // re throw
            throw throwable;
        }
    }

}
