package com.github.houbb.auto.log.core.support.handler;

import com.github.houbb.auto.log.api.IAutoLogContext;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;

/**
 * 大对象丢弃
 *
 * @since 0.10.0
 */
public class AutoLogObjectHandlerBigMemoryDiscard extends AbstractAutoLogObjectHandler {

    private static final Log log = LogFactory.getLog(AutoLogObjectHandlerBigMemoryDiscard.class);

    @Override
    protected Object doHandle(Object rawObject, IAutoLogContext config) {
        // 对象大小
        int maxSize = Math.max(ObjectUtil.getObjectCollectionSize(rawObject), ObjectUtil.getMaxFieldSize(rawObject));

        // 执行丢弃
        if(maxSize >= config.discardSizeLimit()) {
            log.warn("[AutoLog] Discard object with size {} >= limit {}", maxSize, config.discardSizeLimit());
            return null;
        }

        return rawObject;
    }

}
