package com.github.houbb.auto.log.test.service;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.heaven.util.lang.CharUtil;
import com.github.houbb.heaven.util.lang.StringUtil;

import java.util.List;

/**
 * @author d
 * @since 0.11.0
 */
public class DiscardService {

    @AutoLog
    public List<String> queryByIds(List<String> ids) {
        return ids;
    }

    @AutoLog
    public String getString(int len) {
        return CharUtil.repeat('*', len);
    }

}
