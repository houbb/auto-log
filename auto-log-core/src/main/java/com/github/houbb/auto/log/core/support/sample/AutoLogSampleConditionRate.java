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
        if(rate <= 0) {
            return false;
        }
        if(rate >= 100) {
            return true;
        }

        // 随机
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        int value = threadLocalRandom.nextInt(1, 100);

        // 随机概率
        if(rate >= value) {
            return false;
        }

        return true;
    }

}
