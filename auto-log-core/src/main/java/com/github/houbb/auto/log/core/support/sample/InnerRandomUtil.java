package com.github.houbb.auto.log.core.support.sample;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author d
 * @since 0.6.0
 */
public class InnerRandomUtil {

    /**
     * 1. 计算一个 1-100 的随机数 randomVal
     * 2. targetRatePercent 值越大，则返回 true 的概率越高
     * 所以直接根据 targetRatePercent >= randomVal 判断
     *
     * @param targetRatePercent 目标百分比
     * @return 结果
     */
    public static boolean randomRateCondition(int targetRatePercent) {
        if(targetRatePercent <= 0) {
            return false;
        }
        if(targetRatePercent >= 100) {
            return true;
        }

        // 随机
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        int value = threadLocalRandom.nextInt(1, 100);

        // 随机概率
        return targetRatePercent >= value;
    }

}
