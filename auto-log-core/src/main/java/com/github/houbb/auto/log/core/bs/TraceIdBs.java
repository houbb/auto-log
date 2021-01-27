package com.github.houbb.auto.log.core.bs;

import com.github.houbb.auto.log.core.util.AutoLogUtil;
import com.github.houbb.id.api.Id;
import com.github.houbb.id.core.core.Ids;

/**
 * id 引导类
 * @author binbin.hou
 * @since 0.0.8
 */
public final class TraceIdBs {

    private TraceIdBs(){}


    /**
     * id 生成类
     * @since 0.0.8
     */
    private Id id = Ids.uuid32();

    /**
     * 设置 id
     * @param id id
     * @return 实现
     * @since 0.0.8
     */
    public TraceIdBs id(Id id) {
        this.id = id;
        return this;
    }

    /**
     * 创建新的对象
     * @return 对象
     * @since 0.0.8
     */
    public static TraceIdBs newInstance() {
        return new TraceIdBs();
    }

    /**
     * 获取 id 标识
     * @return 标识
     * @since 0.0.8
     */
    public String getTraceId() {
       return AutoLogUtil.get();
    }

    /**
     * 获取 id 标识
     * @return 标识
     * @since 0.0.8
     */
    public static String get() {
        return AutoLogUtil.get();
    }

    /**
     * 移除
     * @since 0.0.8
     */
    public void removeTraceId() {
        AutoLogUtil.removeTraceId();
    }

    /**
     * 不存在时才进行设置
     * @since 0.0.8
     * @return 结果
     */
    public String putIfAbsent() {
        String newTraceId = id.id();
        return this.putIfAbsent(newTraceId);
    }

    /**
     * 进行设置
     * @since 0.0.8
     */
    public void put() {
        String newTraceId = id.id();
        this.put(newTraceId);
    }

    /**
     * 不存在时才进行设置
     * @param newTraceId 新的值
     * @since 0.0.8
     * @return 结果
     */
    public String putIfAbsent(String newTraceId) {
        return AutoLogUtil.putIfAbsent(newTraceId);
    }

    /**
     * 不存在时才进行设置
     * @param newTraceId 新的值
     * @since 0.0.8
     */
    public void put(String newTraceId) {
        AutoLogUtil.put(newTraceId);
    }

}
