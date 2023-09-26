package com.github.houbb.auto.log.core.support.interceptor.schedule;

import com.github.houbb.auto.log.core.support.interceptor.common.AbstractAutoLogCommonFilter;
import com.github.houbb.auto.trace.constants.SpanTypeEnum;
import com.github.houbb.auto.trace.constants.TraceTypeEnum;
import com.github.houbb.auto.trace.interceptor.AbstractAutoTraceProxyInterceptor;
import com.github.houbb.common.filter.annotation.FilterActive;
import com.github.houbb.common.filter.api.Invoker;
import com.github.houbb.common.proxy.core.core.impl.CommonProxyInvocation;
import com.github.houbb.cross.thread.core.constant.CommonScheduleConst;

/**
 * 任务调度的拦截器
 *
 * @since 0.11.0
 */
@FilterActive(group = CommonScheduleConst.COMMON_SCHEDULE_GROUP,
        order = CommonScheduleConst.COMMON_SCHEDULE_ORDER + 1)
public class AutoLogScheduleInterceptor extends AbstractAutoLogCommonFilter {
}
