package com.github.houbb.auto.log.core.support.interceptor.jms;

import com.github.houbb.auto.log.core.support.interceptor.common.AbstractAutoLogCommonFilter;
import com.github.houbb.auto.trace.constants.SpanTypeEnum;
import com.github.houbb.auto.trace.constants.TraceTypeEnum;
import com.github.houbb.auto.trace.interceptor.AbstractAutoTraceProxyInterceptor;
import com.github.houbb.common.filter.annotation.FilterActive;
import com.github.houbb.common.filter.api.Invoker;
import com.github.houbb.common.jms.core.constant.CommonJmsConst;
import com.github.houbb.common.proxy.core.core.impl.CommonProxyInvocation;

/**
 * jms 生产者
 */
@FilterActive(group = CommonJmsConst.GROUP_PRODUCER, order = CommonJmsConst.ORDER_PRODUCER + 1)
public class AutoLogJmsProducerInterceptor extends AbstractAutoLogCommonFilter {

}
