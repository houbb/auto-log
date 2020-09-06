package com.github.houbb.auto.log.core.support.holder;

/**
 * @author binbin.hou
 * @since 0.0.8
 */
public interface IdHolder {

    /**
     * 获取当前线程的 id
     * @return id
     */
    String get();

    /**
     * 设置值
     * @param value 待设置的值
     * @since 0.0.8
     */
    void put(final String value);

    /**
     * 设置值，当不存在的时候
     * @param value 值
     * @return 返回实际设置的值（没有设置，返回原来的值）
     * @since 0.0.8
     */
    String putIfAbsent(final String value);

    /**
     * 移除
     * @since 0.0.8
     */
    void remove();

}
