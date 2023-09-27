package com.github.houbb.auto.log.core.support.handler;

import com.github.houbb.auto.log.api.IAutoLogObjectHandler;
import com.github.houbb.auto.log.api.IAutoLogConfig;

/**
 * @since 0.10.0
 */
public abstract class AbstractAutoLogObjectHandler implements IAutoLogObjectHandler {

    protected abstract Object doHandle(Object rawObject, IAutoLogConfig context);

    @Override
    public Object handle(Object rawObject, IAutoLogConfig context) throws Exception {
        if(rawObject == null) {
            return rawObject;
        }

        return doHandle(rawObject, context);
    }

}
