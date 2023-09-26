package com.github.houbb.auto.log.core.support.interceptor.cache;

import com.github.houbb.auto.log.core.support.interceptor.common.AbstractAutoLogCommonFilter;
import com.github.houbb.common.cache.core.constant.CommonCacheConst;
import com.github.houbb.common.filter.annotation.FilterActive;

@FilterActive(group = CommonCacheConst.COMMON_CACHE_GROUP, order = CommonCacheConst.COMMON_CACHE_ORDER + 1)
public class AutoLogCacheInterceptor extends AbstractAutoLogCommonFilter {
}
