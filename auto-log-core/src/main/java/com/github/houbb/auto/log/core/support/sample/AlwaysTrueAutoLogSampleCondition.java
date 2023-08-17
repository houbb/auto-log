package com.github.houbb.auto.log.core.support.sample;

import com.github.houbb.auto.log.api.IAutoLogSampleCondition;
import com.github.houbb.auto.log.api.IAutoLogSampleConditionContext;

/**
 * 恒为真
 *
 * 1. 基于百分比 0-100
 * 2. 自适应
 *
 * @author d
 * @since 0.5.0
 */
public class AlwaysTrueAutoLogSampleCondition implements IAutoLogSampleCondition {

    @Override
    public boolean sampleCondition(IAutoLogSampleConditionContext context) {
        return true;
    }

}
