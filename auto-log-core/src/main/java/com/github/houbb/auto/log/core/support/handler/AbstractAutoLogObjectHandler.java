package com.github.houbb.auto.log.core.support.handler;

import com.github.houbb.auto.log.api.IAutoLogContext;
import com.github.houbb.auto.log.api.IAutoLogObjectHandler;

/**
 * @since 0.10.0
 */
public abstract class AbstractAutoLogObjectHandler implements IAutoLogObjectHandler {

    protected abstract Object doHandle(Object rawObject, IAutoLogContext context);

    @Override
    public Object handle(Object rawObject, IAutoLogContext context) throws Exception {
        if(rawObject == null) {
            return rawObject;
        }

        return doHandle(rawObject, context);
    }

}
