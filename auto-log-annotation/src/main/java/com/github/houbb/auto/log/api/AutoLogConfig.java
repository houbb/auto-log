package com.github.houbb.auto.log.api;

import com.github.houbb.id.api.Id;

/**
 * 配置信息
 *
 * @since 0.3.0
 */
public class AutoLogConfig {

    /**
     * 是否启用
     * @since 0.0.10
     */
    private boolean enable;

    /**
     * 输出参数
     * @since 0.0.1
     */
    private boolean param;

    /**
     * 是否输出结果
     * @since 0.0.1
     */
    private boolean result;

    /**
     * 是否输出时间
     * @since 0.0.1
     */
    private boolean costTime;

    /**
     * 是否输出异常信息
     * @since 0.0.6
     */
    private boolean exception;

    /**
     * 慢日志阈值
     *
     * 当值小于 0 时，不进行慢日志统计。
     * 当值大于等于0时，当前值只要大于等于这个值，就进行统计。
     * @since 0.0.4
     */
    private long slowThresholdMills;

    /**
     * 方法描述
     * @since 0.0.7
     */
    private String description;

    /**
     * trace id 策略
     * @since 0.0.16
     */
    private Class<? extends Id> traceId;

    /**
     * 是否日志跟踪号，建议只在最外层启用即可。
     * 1. controller
     * 2. mq
     * 3. rpc
     *
     * 所有调用的最外层入口设置为 true 即可, service 层等底层实现设置为 false
     *
     * @since 0.0.16
     */
    private boolean enableTraceId;

    /**
     * 具体的拦截器实现
     * @since 0.0.10
     */
    private Class<? extends IAutoLogInterceptor>[] interceptor;

    /**
     * 指定参数过滤器
     *
     * 作用：过滤 HttpRequest/HttpResponse 等不支持 JSON 序列化的特殊对象
     * @since 0.0.12
     */
    private Class<? extends IParamFilter> paramFilter;

    public boolean enable() {
        return enable;
    }

    public AutoLogConfig setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public boolean param() {
        return param;
    }

    public AutoLogConfig setParam(boolean param) {
        this.param = param;
        return this;
    }

    public boolean result() {
        return result;
    }

    public AutoLogConfig setResult(boolean result) {
        this.result = result;
        return this;
    }

    public boolean costTime() {
        return costTime;
    }

    public AutoLogConfig setCostTime(boolean costTime) {
        this.costTime = costTime;
        return this;
    }

    public boolean exception() {
        return exception;
    }

    public AutoLogConfig setException(boolean exception) {
        this.exception = exception;
        return this;
    }

    public long slowThresholdMills() {
        return slowThresholdMills;
    }

    public AutoLogConfig setSlowThresholdMills(long slowThresholdMills) {
        this.slowThresholdMills = slowThresholdMills;
        return this;
    }

    public String description() {
        return description;
    }

    public AutoLogConfig setDescription(String description) {
        this.description = description;
        return this;
    }

    public Class<? extends Id> traceId() {
        return traceId;
    }

    public AutoLogConfig setTraceId(Class<? extends Id> traceId) {
        this.traceId = traceId;
        return this;
    }

    public boolean enableTraceId() {
        return enableTraceId;
    }

    public AutoLogConfig setEnableTraceId(boolean enableTraceId) {
        this.enableTraceId = enableTraceId;
        return this;
    }

    public Class<? extends IAutoLogInterceptor>[] interceptor() {
        return interceptor;
    }

    public AutoLogConfig setInterceptor(Class<? extends IAutoLogInterceptor>[] interceptor) {
        this.interceptor = interceptor;
        return this;
    }

    public Class<? extends IParamFilter> paramFilter() {
        return paramFilter;
    }

    public AutoLogConfig setParamFilter(Class<? extends IParamFilter> paramFilter) {
        this.paramFilter = paramFilter;
        return this;
    }
}
