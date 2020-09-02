package com.github.houbb.auto.log.core.bs;

import com.github.houbb.aop.core.util.MethodInvocationUtil;
import com.github.houbb.aop.core.util.ProxyUtil;
import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.core.support.interceptor.AutoLogMethodInterceptor;
import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public final class AutoLogBs {

    private static final Log LOG = LogFactory.getLog(AutoLogBs.class);

    private AutoLogBs() {
    }

    /**
     * 代理的对象
     * @since 0.0.6
     */
    private Object target;

    /**
     * 参数
     * @since 0.0.6
     */
    private Object[] params;

    /**
     * 方法
     * @since 0.0.6
     */
    private Method method;

    /**
     * 新建对象实例
     * @return this
     * @since 0.0.3
     */
    public static AutoLogBs newInstance() {
        return new AutoLogBs();
    }


    public AutoLogBs target(Object target) {
        this.target = target;
        return this;
    }

    public AutoLogBs params(Object[] params) {
        this.params = params;
        return this;
    }

    public AutoLogBs method(Method method) {
        this.method = method;
        return this;
    }

    /**
     * 自定日志输出
     * @return 输出
     * @since 0.0.6
     */
    public Object autoLog() {
        String methodName = method.toString();
        AutoLog autoLog = method.getAnnotation(AutoLog.class);
        try {
            final long startMills = System.currentTimeMillis();
            if (ObjectUtil.isNotNull(autoLog)) {
                //1. 是否输入入参
                if (autoLog.param()) {
                    LOG.info("{} param is {}.", methodName, Arrays.toString(params));
                }
            }

            //2. 执行
            Object result = method.invoke(target, params);

            // 没有注解，直接返回结果
            if(ObjectUtil.isNull(autoLog)) {
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
            if (ObjectUtil.isNotNull(autoLog) && autoLog.exception()) {
                LOG.error("{} meet ex.", methodName, throwable);
            }
            // re throw
            throw new RuntimeException(throwable);
        }
    }

}
