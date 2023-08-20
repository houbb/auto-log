package com.github.houbb.auto.log.core.support.sample;

import com.github.houbb.auto.log.api.IAutoLogContext;
import com.github.houbb.auto.log.api.IAutoLogSampleCondition;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 概率采样
 *
 * @author d
 * @since 0.5.0
 */
public class AutoLogSampleConditionRate implements IAutoLogSampleCondition {

    /**
     * 比率
     */
    private final int rate;

    public AutoLogSampleConditionRate(int rate) {
        this.rate = rate;
    }

    public AutoLogSampleConditionRate() {
        this(100);
    }

    @Override
    public boolean sampleCondition(IAutoLogContext context) {
        return InnerRandomUtil.randomRateCondition(rate);
    }

}
