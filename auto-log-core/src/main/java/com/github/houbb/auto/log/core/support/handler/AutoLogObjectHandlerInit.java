package com.github.houbb.auto.log.core.support.handler;

import com.github.houbb.auto.log.api.IAutoLogConfig;
import com.github.houbb.auto.log.api.IAutoLogObjectHandler;
import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.support.pipeline.Pipeline;
import com.github.houbb.heaven.support.pipeline.impl.DefaultPipeline;

import java.util.List;

/**
 * 初始化类
 *
 * @author binbin.hou
 * @since 0.0.13
 */
@ThreadSafe
public abstract class AutoLogObjectHandlerInit implements IAutoLogObjectHandler {

    /**
     * 初始化列表
     *
     * @param pipeline 当前列表泳道
     * @since 0.0.13
     */
    protected abstract void init(final Pipeline<IAutoLogObjectHandler> pipeline);

    @Override
    public Object handle(Object rawObject, IAutoLogConfig context) throws Exception {
        Pipeline<IAutoLogObjectHandler> pipeline = new DefaultPipeline<>();
        this.init(pipeline);

        Object handleResult = rawObject;

        List<IAutoLogObjectHandler> list = pipeline.list();
        for (IAutoLogObjectHandler handler : list) {
            handleResult = handler.handle(handleResult, context);
        }

        return handleResult;
    }

}
