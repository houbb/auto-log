package com.github.houbb.auto.log.core.support.interceptor.chain;

import com.alibaba.fastjson.JSON;
import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.api.IAutoLogContext;
import com.github.houbb.auto.log.api.IAutoLogSampleCondition;
import com.github.houbb.auto.log.core.constant.AutoLogAttachmentKeyConst;
import com.github.houbb.auto.log.core.support.sample.AutoLogSampleConditionAdaptive;
import com.github.houbb.auto.log.core.support.sample.AutoLogSampleConditionRate;
import com.github.houbb.auto.log.core.support.sample.AutoLogSampleConditions;
import com.github.houbb.common.filter.annotation.FilterActive;
import com.github.houbb.common.filter.api.CommonFilter;
import com.github.houbb.common.filter.api.Invocation;
import com.github.houbb.common.filter.api.Invoker;
import com.github.houbb.common.filter.api.Result;
import com.github.houbb.common.filter.exception.CommonFilterException;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectMethodUtil;
import com.github.houbb.id.api.Id;
import com.github.houbb.id.core.core.Ids;
import com.github.houbb.id.core.util.IdThreadLocalHelper;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 默认的日志拦截器
 */
@FilterActive(order = AutoLogFilterChainConst.ORDER, group = AutoLogFilterChainConst.GROUP)
public class AutoLogCommonFilter implements CommonFilter {

    private static final Log LOG = LogFactory.getLog(AutoLogCommonFilter.class);

    /**
     * 是否需要处理日志自动输出
     * @param autoLog 上下文
     * @return 结果
     * @since 0.0.10
     */
    protected boolean enableAutoLog(final AutoLog autoLog) {
        if(autoLog == null) {
            return false;
        }

        return autoLog.enable();
    }

    /**
     * 获取方法描述
     * @param method 方法
     * @param autoLog 注解
     * @return 结果
     * @since 0.0.10
     */
    protected String getMethodDescription(Method method, AutoLog autoLog) {
        String methodName = "";

        if(autoLog != null
            && StringUtil.isNotEmpty(autoLog.description())) {
            methodName = autoLog.description();
        } else {
            methodName = ReflectMethodUtil.getMethodFullName(method);
        }

        return methodName;
    }

    /**
     * 获取 traceId
     * @param autoLog 日志注解
     * @return 结果
     * @since 0.0.10
     */
    protected String getTraceId(AutoLog autoLog) {
        //1. 优先看当前线程中是否存在
        String oldId = IdThreadLocalHelper.get();
        if(StringUtil.isNotEmpty(oldId)) {
            return formatTraceId(oldId);
        }

        //2. 返回对应的标识
        Id id = getActualTraceId(autoLog);
        return formatTraceId(id.id());
    }

    /**
     * 获取日志跟踪号策略
     * @param autoLog 注解
     * @return 没结果
     */
    protected Id getActualTraceId(AutoLog autoLog) {
        Class<? extends Id> idClass = autoLog.traceId();
        if(Id.class.equals(idClass)) {
            return Ids.uuid32();
        }
        return ClassUtil.newInstance(autoLog.traceId());
    }

    /**
     * 格式化日志跟踪号
     * @param id 跟踪号
     * @return 结果
     * @since 0.0.16
     */
    protected String formatTraceId(String id) {
        return String.format("[%s] ", id);
    }

    @Override
    public Result invoke(Invoker invoker, Invocation invocation) throws CommonFilterException {
        final AutoLogInvocation autoLogInvocation = (AutoLogInvocation) invocation;
        final IAutoLogContext autoLogContext = autoLogInvocation.getAutoLogContext();
        final AutoLog autoLog = autoLogContext.autoLog();
        final boolean enableAutoLog = enableAutoLog(autoLog);
        if(!enableAutoLog) {
            return invoker.invoke(invocation);
        }

        final String description = getMethodDescription(autoLogContext.method(), autoLog);
        // 默认从上下文中取一次
        String traceId = IdThreadLocalHelper.get();
        try {
            // 设置 traceId 策略
            if(autoLog.enableTraceId()) {
                Id id = getActualTraceId(autoLog);
                traceId = id.id();

                invocation.setAttachment(AutoLogAttachmentKeyConst.AUTO_LOG_TRACE_ID, traceId);
                IdThreadLocalHelper.put(traceId);
            }

            Result result = invoker.invoke(invocation);

            // 日志增强
            logForEnhance(autoLogContext, traceId, description, result.getValue(), invocation);

            return result;
        } catch (Exception e) {
            if (autoLog.exception()) {
                String message = String.format("[AUTO-LOG][TID=%s][EXCEPTION=%s]", traceId, e.getMessage());
                LOG.error(message, e);
            }

            throw new RuntimeException(e);
        }
    }

    /**
     * 计算采样条件
     * @param autoLogContext 上下文
     * @param traceId 日志跟踪号
     * @param description 方法描述
     * @param resultValue 返回值
     * @param invocation 调用上下文
     * @return 是否
     * @since 0.5.0
     */
    private boolean calcSampleCondition(final IAutoLogContext autoLogContext,
                                        final String traceId,
                                        final String description,
                                        final Object resultValue,
                                        Invocation invocation) {
        final AutoLog autoLog = autoLogContext.autoLog();
        if(autoLog == null) {
            //TODO: 默认实现策略

            return true;
        }

        Class<? extends IAutoLogSampleCondition> sampleConditionClass = autoLog.sampleCondition();

        // 默认
        if(sampleConditionClass.equals(IAutoLogSampleCondition.class)) {
            return true;
        }

        // 如果是概率
        if(sampleConditionClass.equals(AutoLogSampleConditionRate.class)) {
            int sampleRate = autoLog.sampleRate();
            return AutoLogSampleConditions.rate(sampleRate).sampleCondition(autoLogContext);
        } else if(sampleConditionClass.equals(AutoLogSampleConditionAdaptive.class)) {
            // 自适应
            AutoLogSampleConditionAdaptive adaptive = AutoLogSampleConditionAdaptive.getInstance();
            return adaptive.sampleCondition(autoLogContext);
        }

        // 其他
        IAutoLogSampleCondition sampleCondition = ClassUtil.newInstance(sampleConditionClass);
        return sampleCondition.sampleCondition(autoLogContext);
    }

    /**
     * 增强日志输出
     * @param autoLogContext 上下文
     * @param traceId 日志跟踪号
     * @param description 方法描述
     * @param resultValue 返回值
     * @param invocation 调用上下文
     */
    private void logForEnhance(final IAutoLogContext autoLogContext,
                               final String traceId,
                               final String description,
                               final Object resultValue,
                               Invocation invocation) {
        final AutoLogInvocation autoLogInvocation = (AutoLogInvocation) invocation;

        // 采样条件
        boolean condition = calcSampleCondition(autoLogContext, traceId, description, resultValue, invocation);
        if(!condition) {
            return;
        }

        final AutoLog autoLog = autoLogContext.autoLog();

        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("[AUTO-LOG]");
        logBuilder.append(String.format("[TID=%s]", traceId));
        logBuilder.append(String.format("[METHOD=%s]", description));

        // 入参
        if(autoLog.param()) {
            // 构建 params

            Object[] params = autoLogInvocation.getFilterParams();
            logBuilder.append(String.format("[PARAM=%s]", getParamsString(params)));
        }
        // 出参
        if (autoLog.result()) {
            Object actualResult = getActualResult(resultValue);
            logBuilder.append(String.format("[RESULT=%s]", getResultString(actualResult)));
        }
        // 耗时
        //3.1 耗时 & 慢日志
        if(autoLog.costTime()) {
            long startTime = autoLogInvocation.getStartTime();
            long costTime = System.currentTimeMillis() - startTime;
            logBuilder.append(String.format("[COST=%d ms]", costTime));

            // 慢日志
            final long slowThreshold = autoLog.slowThresholdMills();
            if(slowThreshold > 0 && costTime > slowThreshold) {
                logBuilder.append(String.format("[SLOW-THRESHOLD=%s]", slowThreshold));
            }
        }

        // 输出日志
        LOG.info(logBuilder.toString());
    }

    /**
     * 获取参数字符串
     * @param params 入参
     * @return 结果
     */
    protected String getParamsString(Object[] params) {
        if(params == null) {
            return null;
        }

        try {
            return JSON.toJSONString(params);
        } catch (Exception e) {
            return Arrays.toString(params);
        }
    }

    /**
     * 获取结果字符串
     * @param result 结果
     * @return 结果
     */
    protected String getResultString(Object result) {
        if(result == null) {
            return null;
        }

        try {
            return JSON.toJSONString(result);
        } catch (Exception e) {
            return result.toString();
        }
    }

    /**
     * 获取真正的结果只
     * @param resultVal 结果值
     * @return 结构
     * @since 0.7.0
     */
    protected Object getActualResult(Object resultVal) {
        if(resultVal == null) {
            return null;
        }
        // dubbo 处理
        if(resultVal instanceof org.apache.dubbo.rpc.Result) {
            org.apache.dubbo.rpc.Result result = (org.apache.dubbo.rpc.Result) resultVal;
            return result.getValue();
        }

        return resultVal;
    }

}
