package com.github.houbb.auto.log.core.support.sample;

import com.github.houbb.auto.log.api.IAutoLogContext;
import com.github.houbb.auto.log.api.IAutoLogSampleCondition;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自适应采样
 *
 * 1. 初始化采样率为 100%，全部采样
 *
 * 2. QPS 如果越来越高，那么采样率应该越来越低。这样避免 cpu 等资源的损耗。最低为 1%
 * 如果 QPS 越来越低，采样率应该越来越高。增加样本，最高为 100%
 *
 * 3. QPS 如何计算问题
 *
 * 直接设置大小为 100 的队列，每一次在里面放入时间戳。
 * 当大小等于 100 的时候，计算首尾的时间差，currentQps = 100 / (endTime - startTime) * 1000
 *
 * 触发 rate 重新计算。
 *
 * 3.1 rate 计算逻辑
 *
 * 这里我们存储一下 preRate = 100, preQPS = ?
 *
 * newRate = (preQps / currentQps) * rate
 *
 * 范围限制：
 * newRate = Math.min(100, newRate);
 * newRate = Math.max(1, newRate);
 *
 * 3.2 时间队列的清空
 *
 * 更新完 rate 之后，对应的队列可以清空？
 *
 * 如果额外使用一个 count，好像也可以。
 * 可以调整为 atomicLong 的计算器，和 preTime。
 *
 * @author d
 * @since 0.5.0
 */
public class AutoLogSampleConditionAdaptive implements IAutoLogSampleCondition {

    private static final AutoLogSampleConditionAdaptive INSTANCE = new AutoLogSampleConditionAdaptive();

    /**
     * 单例的方式获取实例
     * @return 结果
     */
    public static AutoLogSampleConditionAdaptive getInstance() {
        return INSTANCE;
    }

    /**
     * 次数大小限制，即接收到多少次请求更新一次 adaptive 计算
     *
     * TODO: 这个如何可以让用户可以自定义呢？后续考虑配置从默认的配置文件中读取。
     */
    private static final int COUNT_LIMIT = 1000;

    /**
     * 自适应比率，初始化为 100.全部采集
     */
    private volatile int adaptiveRate = 100;

    /**
     * 上一次的 QPS
     *
     * TODO: 这个如何可以让用户可以自定义呢？后续考虑配置从默认的配置文件中读取。
     */
    private volatile double preQps = 100.0;

    /**
     * 上一次的时间
     */
    private volatile long preTime;

    /**
     * 总数，请求计数器
     */
    private final AtomicInteger counter;

    public AutoLogSampleConditionAdaptive() {
        preTime = System.currentTimeMillis();
        counter = new AtomicInteger(0);
    }

    @Override
    public boolean sampleCondition(IAutoLogContext context) {
        int count = counter.incrementAndGet();

        // 触发一次重新计算
        if(count >= COUNT_LIMIT) {
            updateAdaptiveRate();
        }

        // 直接计算是否满足
        return InnerRandomUtil.randomRateCondition(adaptiveRate);
    }

    /**
     * 更新自适应的概率
     *
     * 100 计算一次，其实还好。实际应该可以适当调大这个阈值，本身不会经常变化的东西。
     */
    private synchronized void updateAdaptiveRate() {
        //消耗的毫秒数
        long costTimeMs = System.currentTimeMillis() - preTime;
        //qps 的计算，时间差是毫秒。所以次数需要乘以 1000
        double currentQps = COUNT_LIMIT*1000.0 / costTimeMs;
        // preRate * preQps = currentRate * currentQps; 保障采样均衡，服务器压力均衡
        // currentRate = (preRate * preQps) / currentQps;
        // 更新比率
        adaptiveRate = (int) ((adaptiveRate * preQps) / currentQps);

        adaptiveRate = Math.min(100, adaptiveRate);
        adaptiveRate = Math.max(1, adaptiveRate);

        // 更新 QPS
        preQps = currentQps;
        // 更新上一次的时间内戳
        preTime = System.currentTimeMillis();
        // 归零
        counter.set(0);
    }


}
