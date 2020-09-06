package com.github.houbb.auto.log.core.core.impl;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.annotation.TraceId;
import com.github.houbb.auto.log.core.bs.TraceIdBs;
import com.github.houbb.auto.log.core.core.IAutoLog;
import com.github.houbb.auto.log.core.core.IAutoLogContext;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.id.api.Id;
import com.github.houbb.id.core.core.Ids;
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
        //1. 日志唯一标识
        TraceId traceId = context.traceId();
        TraceIdBs traceIdBs = null;
        if(traceId != null) {
            Class<? extends Id> idClass = traceId.id();
            Id id = getIdInstance(idClass);

            traceIdBs = TraceIdBs.newInstance().id(id);
            if(traceId.putIfAbsent()) {
                traceIdBs.putIfAbsent();
            } else {
                traceIdBs.put();
            }
        }

        // 日志输出部分
        AutoLog autoLog = context.autoLog();
        final long startMills = System.currentTimeMillis();
        Method method = context.method();
        String description = getMethodDescription(method, autoLog);
        try {
            if(autoLog == null) {
                return context.process();
            }

            String traceIdBefore = getTraceId(autoLog);

            //1. 是否输入入参
            if (autoLog.param()) {
                Object[] params = context.params();
                String paramsLog = String.format("<%s>入参: %s.",
                        description, Arrays.toString(params));
                LOG.info(traceIdBefore + paramsLog);
            }

            //2. 执行结果
            Object result = context.process();

            // 避免方法中设置
            String traceIdAfter = getTraceId(autoLog);

            //3. 结果
            if (autoLog.result()) {
                String resultLog = String.format("<%s>出参：%s.", description, result);
                LOG.info(traceIdAfter+resultLog);
            }

            //3.1 耗时 & 慢日志
            final long slowThreshold = autoLog.slowThresholdMills();
            if(autoLog.costTime() || autoLog.slowThresholdMills() >= 0) {
                final long endMills = System.currentTimeMillis();
                long costTime = endMills - startMills;
                if (autoLog.costTime()) {
                    String costTimeLog = String.format("<%s>耗时：%sms.", description, costTime);
                    LOG.info(traceIdAfter+costTimeLog);
                }

                //3.2 慢日志
                if (slowThreshold >= 0 && costTime >= slowThreshold) {
                    String slowLog = String.format("<%s>慢日志, %sms >= %sms.",
                            description, costTime, slowThreshold);
                    LOG.warn(traceIdAfter+slowLog);
                }
            }

            return result;
        } catch (Exception e) {
            if (autoLog.exception()) {
                String errorLog = String.format("<%s>异常", description);
                String traceIdError = getTraceId(autoLog);
                LOG.error(traceIdError+errorLog, e);
            }
            // re throw
            throw new RuntimeException(e);
        } finally {
            if(traceIdBs != null) {
                traceIdBs.removeTraceId();
            }
        }
    }

    /**
     * 新建对象实例
     * @param idClass id 类
     * @return 实现
     * @since 0.0.8
     */
    private Id getIdInstance(final Class<? extends Id> idClass) {
        if(Id.class.equals(idClass)) {
            return Ids.uuid32();
        }
        return ClassUtil.newInstance(idClass);
    }

    /**
     * 获取方法描述
     * @param method 方法
     * @param autoLog 注解
     * @return 结果
     * @since 0.0.7
     */
    private String getMethodDescription(Method method, AutoLog autoLog) {
        if(autoLog == null) {
            return method.getName();
        }

        String description = autoLog.description();
        if(StringUtil.isNotEmpty(description)) {
            return description;
        }

        return method.getName();
    }

    /**
     * 获取 traceId
     * @param autoLog 日志注解
     * @return 结果
     * @since 0.0.8
     */
    private String getTraceId(AutoLog autoLog) {
        if(!autoLog.traceId()) {
            return StringUtil.EMPTY;
        }

        String traceId = TraceIdBs.get();
        if(StringUtil.isEmpty(traceId)) {
            return StringUtil.EMPTY;
        }

        return String.format("[%s] ", traceId);
    }

}
