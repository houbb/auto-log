package com.github.houbb.auto.log.core.support.handler;

import com.github.houbb.auto.log.api.IAutoLogObjectHandler;
import com.github.houbb.heaven.support.pipeline.Pipeline;
import com.github.houbb.heaven.util.util.ArrayUtil;

/**
 *
 * @since 0.11.0
 */
public class AutoLogObjectHandlers {

    /**
     * 责任链
     * @param objectHandler 对象处理
     * @param others 其他
     * @return 结果
     */
    public static IAutoLogObjectHandler chains(final IAutoLogObjectHandler objectHandler,
                                               final IAutoLogObjectHandler... others) {
        return new AutoLogObjectHandlerInit() {
            @Override
            protected void init(Pipeline<IAutoLogObjectHandler> pipeline) {
                pipeline.addLast(objectHandler);

                if(ArrayUtil.isNotEmpty(others)) {
                    for(IAutoLogObjectHandler other : others) {
                        pipeline.addLast(other);
                    }
                }
            }
        };
    }

    /**
     * 默认策略
     * @return 策略
     */
    public static IAutoLogObjectHandler defaults() {
        return chains(webDiscard(), dubboResult(), bigMemoryDiscard());
    }

    /**
     * 原始对象
     * @return 原始
     */
    public static IAutoLogObjectHandler raw() {
        return new AutoLogObjectHandlerRaw();
    }

    /**
     * web 丢弃
     * @return 原始
     */
    public static IAutoLogObjectHandler webDiscard() {
        return new AutoLogObjectHandlerWebDiscard();
    }

    /**
     * 大对象 丢弃
     * @return 原始
     */
    public static IAutoLogObjectHandler bigMemoryDiscard() {
        return new AutoLogObjectHandlerBigMemoryDiscard();
    }

    /**
     * dubbo 结果
     * @return 原始
     * @since 0.11.0
     */
    public static IAutoLogObjectHandler dubboResult() {
        return new AutoLogObjectHandlerDubboResult();
    }

}
