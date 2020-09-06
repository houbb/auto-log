package com.github.houbb.auto.log.core.core.impl;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.core.core.IAutoLog;
import com.github.houbb.auto.log.core.core.IAutoLogContext;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author binbin.hou
 * @since 0.0.7
 */
public class SimpleAutoLog implements IAutoLog {

    private static final Log LOG = LogFactory.getLog(SimpleAutoLog.class);

    /**
     * 自动日志输出
     *
     * @param context 上下文
     * @return 结果
     * @since 0.0.7
     */
    @Override
    public Object autoLog(IAutoLogContext context) throws Throwable {
        AutoLog autoLog = context.autoLog();
        if(autoLog == null) {
            return context.process();
        }

        final long startMills = System.currentTimeMillis();
        Method method = context.method();
        String description = getMethodDescription(method, autoLog);
        try {
            //1. 是否输入入参
            if (autoLog.param()) {
                Object[] params = context.params();
                LOG.info("<{}>入参: {}.", description, Arrays.toString(params));
            }

            //2. 执行结果
            Object result = context.process();

            //3. 结果
            if (autoLog.result()) {
                LOG.info("<{}>出参：{}.", description, result);
            }

            //3.1 耗时 & 慢日志
            final long slowThreshold = autoLog.slowThresholdMills();
            if(autoLog.costTime() || autoLog.slowThresholdMills() >= 0) {
                final long endMills = System.currentTimeMillis();
                long costTime = endMills - startMills;
                if (autoLog.costTime()) {
                    LOG.info("<{}>耗时：{}ms.", description, costTime);
                }

                //3.2 慢日志
                if (slowThreshold >= 0 && costTime >= slowThreshold) {
                    LOG.warn("<{}>慢日志, {}ms >= {}ms.", description, costTime, slowThreshold);
                }
            }

            return result;
        } catch (Exception e) {
            if (autoLog.exception()) {
                LOG.error("<{}>异常", description, e);
            }
            // re throw
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取方法描述
     * @param method 方法
     * @param autoLog 注解
     * @return 结果
     * @since 0.0.7
     */
    private String getMethodDescription(Method method, AutoLog autoLog) {
        String description = autoLog.description();
        if(StringUtil.isNotEmpty(description)) {
            return description;
        }

        return method.getName();
    }

}
