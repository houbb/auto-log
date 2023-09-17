package com.github.houbb.auto.log.core.support.dubbo.filter;


import com.github.houbb.auto.log.core.support.dubbo.AutoLogDubboConst;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;

/**
 * @since 0.10.0
 */
@Activate(group = CommonConstants.PROVIDER, order = AutoLogDubboConst.PROVIDER_ORDER)
public class AutoLogDubboProviderFilter extends AbstractAutoLogDubboFilter {

}
