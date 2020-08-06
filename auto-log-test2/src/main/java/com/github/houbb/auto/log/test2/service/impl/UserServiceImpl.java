package com.github.houbb.auto.log.test2.service.impl;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.test2.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author binbin.hou
 * @since 0.0.5
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    @AutoLog
    public String query(final String id) {
        return "result-" + id;
    }

}
