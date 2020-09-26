package com.github.houbb.auto.log.core.support.filter.param;

import com.github.houbb.auto.log.core.util.ClassHelper;
import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;

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
                Class<?> clazz = param.getClass();
                if(ClassHelper.instanceOf(clazz, "javax.servlet.ServletRequest") || ClassHelper.instanceOf(clazz, "javax.servlet.ServletResponse") || ClassHelper.instanceOf(clazz, "org.springframework.web.multipart.MultipartFile")) {
                    param = null;
                }
            }

            filters[i] = param;
        }

        return filters;
    }

}
