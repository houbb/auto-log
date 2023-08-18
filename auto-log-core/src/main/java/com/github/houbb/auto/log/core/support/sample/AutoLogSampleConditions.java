package com.github.houbb.auto.log.core.support.sample;

import com.github.houbb.auto.log.api.IAutoLogSampleCondition;

/**
 * @author d
 * @since 0.5.0
 */
public class AutoLogSampleConditions {

    /**
     * 概率
     * @param rate 概率
     * @return 结果
     * @since 0.5.0
     */
    public static IAutoLogSampleCondition rate(final int rate) {
        return new AutoLogSampleConditionRate(rate);
    }
}
