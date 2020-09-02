package com.github.houbb.auto.log.test.service.impl;

import com.github.houbb.auto.log.annotation.AutoLog;
import org.springframework.stereotype.Service;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
@Service
public class MenuServiceImpl {

    @AutoLog
    public String query(final String id) {
        return "result-" + id;
    }

}
