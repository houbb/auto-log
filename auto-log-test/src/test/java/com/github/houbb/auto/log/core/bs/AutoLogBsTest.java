package com.github.houbb.auto.log.core.bs;

import com.github.houbb.auto.log.test.service.UserService;
import com.github.houbb.auto.log.test.service.impl.UserServiceImpl;
import org.junit.Test;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public class AutoLogBsTest {

    @Test
    public void proxyTest() {
        UserService userService = AutoLogBs.proxy(new UserServiceImpl());

        userService.queryLog("1");
    }

}
