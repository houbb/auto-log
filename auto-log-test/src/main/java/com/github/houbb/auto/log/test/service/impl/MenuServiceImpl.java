package com.github.houbb.auto.log.test.service.impl;

import org.springframework.stereotype.Service;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
@Service
public class MenuServiceImpl {

    public String query(final String id) {
        return "result-" + id;
    }

}
