package com.github.houbb.auto.log.core.support.interceptor.web.client;

import com.github.houbb.auto.log.core.support.interceptor.common.AbstractAutoLogCommonFilter;
import com.github.houbb.auto.trace.constants.AutoTraceConst;
import com.github.houbb.common.filter.annotation.FilterActive;
import com.github.houbb.http.client.core.constant.HttpClientGroupConst;

/**
 * 客户端通用拦截器
 * @since 0.5.0
 */
@FilterActive(order = AutoTraceConst.ASPECT_ORDER + 1,
        group = HttpClientGroupConst.HTTP_CLIENT_GROUP)
public class AutoLogHttpClientInterceptor extends AbstractAutoLogCommonFilter {
}
