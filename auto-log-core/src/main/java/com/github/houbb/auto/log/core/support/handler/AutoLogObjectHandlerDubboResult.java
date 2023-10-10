package com.github.houbb.auto.log.core.support.handler;

import com.github.houbb.auto.log.api.IAutoLogContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * dubbo 层的一些特殊对象
 *
 * @since 0.11.0
 */
public class AutoLogObjectHandlerDubboResult extends AbstractAutoLogObjectHandler {

    @Override
    protected Object doHandle(Object rawObject, IAutoLogContext context) {
        if(rawObject instanceof org.apache.dubbo.rpc.Result) {
            org.apache.dubbo.rpc.Result result = (org.apache.dubbo.rpc.Result) rawObject;
            return result.getValue();
        }

        return rawObject;
    }

}
