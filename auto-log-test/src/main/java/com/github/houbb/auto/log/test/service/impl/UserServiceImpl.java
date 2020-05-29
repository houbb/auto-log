package com.github.houbb.auto.log.test.service.impl;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.test.service.UserService;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public class UserServiceImpl implements UserService {

    @Override
    public String query(final String id) {
        return "result-"+id;
    }

    @Override
    @AutoLog
    public String queryLog(String id) {
        return this.query(id);
    }

}
