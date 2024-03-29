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
//@AutoLog
public class UserServiceImpl implements UserService {

    @Override
    public String query(final String id) {
        return "result-" + id;
    }

    @Override
    @AutoLog(description = "查询日志", enableTraceId = true)
    public String queryLog(String id) {
        return this.query(id);
    }

    @AutoLog(description = "私有查询日志")
    private String privateQueryLog(String id) {
        return this.query(id);
    }

    @Override
    @AutoLog(slowThresholdMills = 1000)
    public String slowLog(String id) {
        try {
            TimeUnit.SECONDS.sleep(2);
            return "slow-" + id;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @AutoLog
    public String traceId(String id) {
        return id+"-1";
    }

}
