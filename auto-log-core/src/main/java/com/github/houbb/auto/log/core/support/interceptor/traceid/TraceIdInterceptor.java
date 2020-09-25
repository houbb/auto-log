package com.github.houbb.auto.log.core.support.interceptor.traceid;

import com.github.houbb.auto.log.annotation.TraceId;
import com.github.houbb.auto.log.api.ITraceIdInterceptorContext;
import com.github.houbb.auto.log.core.bs.TraceIdBs;
import com.github.houbb.id.api.Id;

/**
 * @author binbin.hou
 * @since 0.0.10
 */
public class TraceIdInterceptor extends AbstractTraceIdInterceptor {

    @Override
    protected void doBefore(TraceId traceId, ITraceIdInterceptorContext context) {
        Class<? extends Id> idClass = traceId.id();
        Id id = getIdInstance(idClass);

        TraceIdBs traceIdBs = TraceIdBs.newInstance().id(id);
        if(traceId.putIfAbsent()) {
            traceIdBs.putIfAbsent();
        } else {
            traceIdBs.put();
        }
    }

    @Override
    protected void doFinally(TraceId traceId, ITraceIdInterceptorContext context) {
        TraceIdBs.newInstance().removeTraceId();
    }

}
