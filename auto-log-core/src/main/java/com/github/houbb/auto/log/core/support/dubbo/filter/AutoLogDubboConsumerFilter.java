package com.github.houbb.auto.log.core.support.dubbo.filter;

import com.github.houbb.auto.log.core.support.dubbo.AutoLogDubboConst;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;

/**
 * 消费者-客户端
 * @since 0.10.0
 */
@Activate(group = CommonConstants.CONSUMER, order = AutoLogDubboConst.CONSUMER_ORDER)
public class AutoLogDubboConsumerFilter extends AbstractAutoLogDubboFilter {

}
