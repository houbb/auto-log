package com.github.houbb.auto.log.test.service.impl;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.test.service.UserService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
@Service
public class PrivateUserServiceImpl {

    @AutoLog(description = "私有查询日志")
    private String privateQueryLog() {
        return "private";
    }

    @AutoLog(description = "public查询日志")
    public void callPrivate() {
        String result = privateQueryLog();

        System.out.println("done");
    }

}
