package com.github.houbb.auto.log.test.service.impl;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.test.filter.MyParamFilter;
import com.github.houbb.auto.log.test.interceptor.MyAutoLogInterceptor;
import org.springframework.stereotype.Service;

/**
 * 自定义实现类
 * @author binbin.hou
 * @since 0.0.10
 */
@Service
public class DefineService {

    /**
     * @return 结果
     * @since 0.0.10
     */
    @AutoLog(interceptor = MyAutoLogInterceptor.class)
    public String interceptor() {
        return "自定义策略";
    }

    /**
     * @return 结果
     * @since 0.0.12
     */
    @AutoLog(paramFilter = MyParamFilter.class)
    public String paramFilter() {
        return "自定义入参过滤器";
    }

}
