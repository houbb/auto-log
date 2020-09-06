package com.github.houbb.auto.log.core.support.holder.impl;


import com.github.houbb.auto.log.core.support.holder.IdHolder;

/**
 * @author binbin.hou
 * @since 0.0.8
 */
public final class IdHolders {

    private IdHolders(){}

    /**
     * 基于 threadLocal 的实现
     * @return 实现
     * @since 0.0.8
     */
    public static IdHolder threadLocal() {
        return IdHolderThreadLocal.getInstance();
    }

}
