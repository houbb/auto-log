package com.github.houbb.auto.log.spring;


import com.github.houbb.auto.log.test.config.SpringConfig;
import com.github.houbb.auto.log.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author binbin.hou
 * @since 0.0.3
 */
@ContextConfiguration(classes = SpringConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void queryLogTest() {
        userService.queryLog("1");
    }

    @Test
    public void queryTest() {
        userService.query("1");
    }

    @Test
    public void queryTraceIdTest() {
        userService.traceId("1");
    }

}
