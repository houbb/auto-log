package com.github.houbb.auto.log.test.service.impl;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.core.support.sample.AutoLogSampleConditionRate;
import org.springframework.stereotype.Service;

/**
 * @author binbin.hou
 * @since 0.5.0
 */
@Service
public class SampleRateServiceImpl {

    @AutoLog(sampleCondition = AutoLogSampleConditionRate.class,
            sampleRate = 30)
    public void sample30Test(int value) {
        System.out.println("i==" + value);
    }

    @AutoLog(sampleCondition = AutoLogSampleConditionRate.class,
        sampleRate = 50)
    public void sample50Test(int value) {
        System.out.println("i==" + value);
    }

    @AutoLog(sampleCondition = AutoLogSampleConditionRate.class,
            sampleRate = 0)
    public void sample0Test(int value) {
        System.out.println("i==" + value);
    }

    @AutoLog(sampleCondition = AutoLogSampleConditionRate.class,
            sampleRate = 100)
    public void sample100Test(int value) {
        System.out.println("i==" + value);
    }

}
