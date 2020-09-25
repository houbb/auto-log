package com.github.houbb.auto.log.core.support.interceptor.autolog;

import com.alibaba.fastjson.JSON;
import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.api.IAutoLogInterceptorContext;
import com.github.houbb.auto.log.core.util.ClassHelper;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认的方法实现
 * @author binbin.hou
 * @since 0.0.10
 */
public class AutoLogInterceptor extends AbstractAutoLogInterceptor {

    private static final Log LOG = LogFactory.getLog(AutoLogInterceptor.class);

    @Override
    protected void doBefore(AutoLog autoLog, IAutoLogInterceptorContext context) {
        if(autoLog.param()) {
            String description = super.getMethodDescription(context.method(), autoLog);
            Object[] params = context.params();
            List<Object> filterParams = filterParams(params);
            String paramsLog = String.format("<%s>入参: %s.",
                    description, JSON.toJSON(filterParams));

            String traceIdBefore = super.getTraceId(autoLog);
            LOG.info(traceIdBefore + paramsLog);
        }
    }

    // 入参需要过滤掉 HttpServletRequest && HttpServletResponse
    private List<Object> filterParams(Object[] params) {
        List<Object> resultList = new ArrayList<>();
        if(ArrayUtil.isEmpty(params)) {
            return resultList;
        }

        for(Object param : params) {
            if(ObjectUtil.isNull(param)) {
                resultList.add(param);
            }

            Class<?> clazz = param.getClass();
            if(ClassHelper.instanceOf(clazz, "javax.servlet.ServletRequest") || ClassHelper.instanceOf(clazz, "javax.servlet.ServletResponse") || ClassHelper.instanceOf(clazz, "org.springframework.web.multipart.MultipartFile")) {
                continue;
            }

            resultList.add(param);
        }
        return resultList;
    }

    @Override
    protected void doAfter(AutoLog autoLog, Object result, IAutoLogInterceptorContext context) {

        // 避免方法中设置
        String traceIdAfter = getTraceId(autoLog);
        String description = super.getMethodDescription(context.method(), autoLog);

        if (autoLog.result()) {
            String resultJson = JSON.toJSONString(result);
            String resultLog = String.format("<%s>出参：%s.", description, resultJson);
            LOG.info(traceIdAfter+resultLog);
        }

        //3.1 耗时 & 慢日志
        final long slowThreshold = autoLog.slowThresholdMills();
        if(autoLog.costTime() || autoLog.slowThresholdMills() >= 0) {
            long costTime = context.endTime() - context.startTime();
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
    }

    @Override
    protected void doException(AutoLog autoLog, Exception exception, IAutoLogInterceptorContext context) {
        if (autoLog.exception()) {
            String description = super.getMethodDescription(context.method(), autoLog);

            String errorLog = String.format("<%s>异常", description);
            String traceIdError = getTraceId(autoLog);
            LOG.error(traceIdError+errorLog, exception);
        }
    }

}
