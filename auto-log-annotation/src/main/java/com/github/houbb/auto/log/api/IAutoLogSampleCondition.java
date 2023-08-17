package com.github.houbb.auto.log.api;

/**
 * 采样条件
 * @author binbin.hou
 * @since 0.5.0
 */
public interface IAutoLogSampleCondition {

    /**
     * 条件
     *
     * @param context 上下文
     * @return 结果
     * @since 0.5.0
     */
    boolean sampleCondition(IAutoLogSampleConditionContext context);

}
