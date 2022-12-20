package com.github.houbb.auto.log.core.support.interceptor.autolog;

import com.alibaba.fastjson.JSON;
import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.api.IAutoLogInterceptorContext;
import com.github.houbb.id.api.Id;
import com.github.houbb.id.core.util.IdThreadLocalHelper;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;

/**
 * 默认的方法实现
 * @author binbin.hou
 * @since 0.0.10
 */
public class AutoLogInterceptor extends AbstractAutoLogInterceptor {

    private static final Log LOG = LogFactory.getLog(AutoLogInterceptor.class);

    @Override
    protected void doBefore(AutoLog autoLog, IAutoLogInterceptorContext context) {
        // 设置 traceId 策略
        // 通过强制设置的方式，所以不做移除操作也行
        if(autoLog.enableTraceId()) {
            Id id = getActualTraceId(autoLog);
            IdThreadLocalHelper.put(id.id());
        }

        if(autoLog.param()) {
            String description = super.getMethodDescription(context.method(), autoLog);
            Object[] params = context.filterParams();
            String paramsLog = String.format("<%s> PARAM: %s.", description, JSON.toJSON(params));

            String traceIdBefore = super.getTraceId(autoLog);
            LOG.info(traceIdBefore + paramsLog);
        }
    }

    @Override
    protected void doAfter(AutoLog autoLog, Object result, IAutoLogInterceptorContext context) {
        // 避免方法中设置
        String traceIdAfter = getTraceId(autoLog);
        String description = super.getMethodDescription(context.method(), autoLog);

        if (autoLog.result()) {
            String resultJson = JSON.toJSONString(result);
            String resultLog = String.format("<%s> RESULT：%s.", description, resultJson);
            LOG.info(traceIdAfter+resultLog);
        }

        //3.1 耗时 & 慢日志
        final long slowThreshold = autoLog.slowThresholdMills();
        if(autoLog.costTime() || autoLog.slowThresholdMills() >= 0) {
            long costTime = context.endTime() - context.startTime();
            if (autoLog.costTime()) {
                String costTimeLog = String.format("<%s> COST：%sms.", description, costTime);
                LOG.info(traceIdAfter+costTimeLog);
            }

            //3.2 慢日志
            if (slowThreshold >= 0 && costTime >= slowThreshold) {
                String slowLog = String.format("<%s> SLOW LOG, %sms >= %sms.",
                        description, costTime, slowThreshold);
                LOG.warn(traceIdAfter+slowLog);
            }
        }
    }

    @Override
    protected void doException(AutoLog autoLog, Exception exception, IAutoLogInterceptorContext context) {
        if (autoLog.exception()) {
            String description = super.getMethodDescription(context.method(), autoLog);

            String errorLog = String.format("<%s> EXCEPTION", description);
            String traceIdError = getTraceId(autoLog);
            LOG.error(traceIdError+errorLog, exception);
        }
    }

}
