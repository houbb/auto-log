package com.github.houbb.auto.log.core.support.interceptor.dubbo;

import com.github.houbb.auto.trace.constants.AutoTraceDubboConst;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;

/**
 * 消费者-客户端
 * @since 0.10.0
 */
@Activate(group = CommonConstants.CONSUMER, order = AutoTraceDubboConst.CONSUMER_ORDER + 1)
public class AutoLogDubboConsumerFilter extends AbstractAutoLogDubboFilter {

}
