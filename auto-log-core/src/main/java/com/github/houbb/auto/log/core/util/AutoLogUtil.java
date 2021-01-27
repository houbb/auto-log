package com.github.houbb.auto.log.core.util;

import com.github.houbb.auto.log.core.support.holder.IdHolder;
import com.github.houbb.auto.log.core.support.holder.impl.IdHolders;
import com.github.houbb.heaven.util.lang.StringUtil;

/**
 * 自动日志工具类
 *
 * （1）将工具提取出来，便于使用者获取当前的 traceId 信息
 *
 * @author binbin.hou
 * @since 0.0.14
 */
public final class AutoLogUtil {

    private AutoLogUtil(){}

    /**
     * id 持有类
     * @since 0.0.8
     */
    private static final IdHolder ID_HOLDER = IdHolders.threadLocal();

    /**
     * 获取 id 标识
     * @return 标识
     * @since 0.0.8
     */
    public static String getTraceId() {
        return ID_HOLDER.get();
    }

    /**
     * 获取 id 标识
     * @return 标识
     * @since 0.0.8
     */
    public static String get() {
        return ID_HOLDER.get();
    }

    /**
     * 移除
     * @since 0.0.8
     */
    public static void removeTraceId() {
        ID_HOLDER.remove();
    }

    /**
     * 不存在时才进行设置
     * @param newTraceId 新的值
     * @since 0.0.8
     * @return 结果
     */
    public static String putIfAbsent(String newTraceId) {
        String traceId = ID_HOLDER.get();
        if(StringUtil.isEmpty(traceId)) {
            traceId = newTraceId;

            ID_HOLDER.put(traceId);
        }

        return traceId;
    }

    /**
     * 不存在时才进行设置
     * @param newTraceId 新的值
     * @since 0.0.8
     */
    public static void put(String newTraceId) {
        ID_HOLDER.put(newTraceId);
    }

}
