package com.github.houbb.auto.log.core.support.holder.impl;

import com.github.houbb.auto.log.core.support.holder.IdHolder;
import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.lang.StringUtil;

/**
 * @author binbin.hou
 * @since 0.0.8
 */
@ThreadSafe
public class IdHolderThreadLocal implements IdHolder {

    /**
     * 本地线程
     * @since 0.0.1
     */
    private static final ThreadLocal<String> STRING_THREAD_LOCAL = new ThreadLocal<>();

    private static class SingletonHolder {
        private static final IdHolderThreadLocal INSTANCE = new IdHolderThreadLocal();
    }

    /**
     * 构造器私有化
     */
    private IdHolderThreadLocal(){}

    /**
     * 单例模式
     * @return 单例
     * @since 0.0.1
     */
    public static IdHolderThreadLocal getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String get() {
        return STRING_THREAD_LOCAL.get();
    }

    @Override
    public void put(String value) {
        STRING_THREAD_LOCAL.set(value);
    }

    @Override
    public String putIfAbsent(String value) {
        String result = get();

        if(StringUtil.isEmpty(result)) {
            put(value);
            result = value;
        }

        return result;
    }

    @Override
    public void remove() {
        STRING_THREAD_LOCAL.remove();
    }

}
