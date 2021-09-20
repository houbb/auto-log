package com.github.houbb.auto.log.core.support.filter.param;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * web 过滤器
 *
 * @author binbin.hou
 * @since 0.0.12
 */
@ThreadSafe
public class WebParamFilter extends AbstractParamFilter {

    @Override
    protected Object[] doFilter(Object[] params) {
        if(ArrayUtil.isEmpty(params)) {
            return params;
        }

        Object[] filters = new Object[params.length];
        for(int i = 0; i < params.length; i++) {
            Object param = params[i];
            // 过滤掉特殊的入参
            if(ObjectUtil.isNotNull(param)) {
                if(param instanceof ServletRequest ||
                        param instanceof ServletResponse ||
                        param instanceof MultipartFile) {
                    param = null;
                }
            }

            filters[i] = param;
        }

        return filters;
    }

}
