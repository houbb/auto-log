package com.github.houbb.auto.log.core.support.sample;

import com.github.houbb.auto.log.api.IAutoLogContext;
import com.github.houbb.auto.log.api.IAutoLogSampleCondition;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 自适应采样-时间窗口
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
public class AutoLogSampleConditionAdaptiveSchedule implements IAutoLogSampleCondition {

    private static final AutoLogSampleConditionAdaptiveSchedule INSTANCE = new AutoLogSampleConditionAdaptiveSchedule();

    /**
     * 单例的方式获取实例
     * @return 结果
     */
    public static AutoLogSampleConditionAdaptiveSchedule getInstance() {
        return INSTANCE;
    }

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    /**
     * 时间分钟间隔
     */
    private static final int TIME_INTERVAL_MINUTES = 5;

    /**
     * 自适应比率，初始化为 100.全部采集
     */
    private volatile int adaptiveRate = 100;

    /**
     * 上一次的总数
     *
     * TODO: 这个如何可以让用户可以自定义呢？后续考虑配置从默认的配置文件中读取。
     */
    private volatile long preCount;

    /**
     * 总数，请求计数器
     */
    private final AtomicLong counter;

    public AutoLogSampleConditionAdaptiveSchedule() {
        counter = new AtomicLong(0);
        preCount = TIME_INTERVAL_MINUTES * 60 * 100;

        //1. 1min 后开始执行
        //2. 中间默认 5 分钟更新一次
        EXECUTOR_SERVICE.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                updateAdaptiveRate();
            }
        }, 60, TIME_INTERVAL_MINUTES * 60, TimeUnit.SECONDS);
    }

    @Override
    public boolean sampleCondition(IAutoLogContext context) {
        counter.incrementAndGet();

        // 直接计算是否满足
        return InnerRandomUtil.randomRateCondition(adaptiveRate);
    }

    /**
     * 更新自适应的概率
     *
     * QPS = count / time_interval
     *
     * 其中时间维度是固定的，所以可以不用考虑时间。
     */
    private synchronized void updateAdaptiveRate() {
        // preRate * preCount = currentRate * currentCount; 保障采样均衡，服务器压力均衡
        // currentRate = (preRate * preCount) / currentCount;
        // 更新比率
        long currentCount = counter.get();
        int newRate = 100;
        if(currentCount != 0) {
            newRate = (int) ((adaptiveRate * preCount) / currentCount);
            newRate = Math.min(100, newRate);
            newRate = Math.max(1, newRate);
        }

        // 更新自适应频率
        adaptiveRate = newRate;

        // 更新 QPS
        preCount = currentCount;
        // 归零
        counter.set(0);
    }


}
