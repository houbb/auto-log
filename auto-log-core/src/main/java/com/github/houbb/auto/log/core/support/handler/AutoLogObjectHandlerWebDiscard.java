package com.github.houbb.auto.log.core.support.handler;

import com.github.houbb.auto.log.api.IAutoLogContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * web 层的一些特殊对象
 *
 * @since 0.10.0
 */
public class AutoLogObjectHandlerWebDiscard extends AbstractAutoLogObjectHandler {

    @Override
    protected Object doHandle(Object rawObject, IAutoLogContext context) {
        if(rawObject instanceof ServletRequest ||
                rawObject instanceof ServletResponse ||
                rawObject instanceof MultipartFile) {
            return null;
        }

        return rawObject;
    }

}
