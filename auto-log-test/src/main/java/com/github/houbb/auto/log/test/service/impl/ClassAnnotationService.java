package com.github.houbb.auto.log.test.service.impl;

import com.github.houbb.auto.log.annotation.AutoLog;
import org.springframework.stereotype.Service;

/**
 * @author binbin.hou
 * @since 0.0.10
 */
@Service
@AutoLog(costTime = true)
public class ClassAnnotationService {

    public String enable() {
        return "enable";
    }

    @AutoLog(enable = false)
    public String disable() {
        return "disable";
    }

}
