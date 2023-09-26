package com.github.houbb.auto.log.core.support.interceptor.dubbo;


import com.github.houbb.auto.trace.constants.AutoTraceDubboConst;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;

/**
 * @since 1.0.0
 */
@Activate(group = CommonConstants.PROVIDER, order = AutoTraceDubboConst.PROVIDER_ORDER + 1)
public class AutoLogDubboProviderFilter extends AbstractAutoLogDubboFilter {

}
