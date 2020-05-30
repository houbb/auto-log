package com.github.houbb.auto.log.core.util;

import com.github.houbb.auto.log.core.bs.AutoLogBs;

/**
 * 自动日志工具类
 * @author binbin.hou
 * @since 0.0.3
 */
public final class AutoLogHelper {

    private AutoLogHelper(){}

    /**
     * 生成对象的代理
     * @param object 对象
     * @param <R> 泛型
     * @return 结果
     * @since 0.0.3
     */
    public static <R> R proxy(final R object) {
        return AutoLogBs.newInstance().proxy(object);
    }

}
