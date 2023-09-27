package com.github.houbb.auto.log.core.core;

import com.github.houbb.auto.log.api.IAutoLogConfig;

/**
 * 对象处理器上下文
 *
 * @author binbin.hou
 * @since 0.10.0
 */
public class AutoLogConfig implements IAutoLogConfig {

    /**
     * 丢弃的大小限制
     */
    private int discardSizeLimit;

    @Override
    public int getDiscardSizeLimit() {
        return discardSizeLimit;
    }

    public void setDiscardSizeLimit(int discardSizeLimit) {
        this.discardSizeLimit = discardSizeLimit;
    }
}
