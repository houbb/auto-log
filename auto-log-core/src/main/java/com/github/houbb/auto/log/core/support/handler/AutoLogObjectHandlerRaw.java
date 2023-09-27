package com.github.houbb.auto.log.core.support.handler;

import com.github.houbb.auto.log.api.IAutoLogContext;

/**
 * 不做任何处理
 *
 * @since 0.10.0
 */
public class AutoLogObjectHandlerRaw extends AbstractAutoLogObjectHandler {

    @Override
    protected Object doHandle(Object rawObject, IAutoLogContext context) {
        return rawObject;
    }

}
