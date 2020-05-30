package com.github.houbb.auto.log.core.util;

import com.github.houbb.auto.log.core.bs.AutoLogBs;
import com.github.houbb.auto.log.test.service.UserService;
import com.github.houbb.auto.log.test.service.impl.UserServiceImpl;
import org.junit.Test;

/**
 * @author binbin.hou
 * @since 0.0.3
 */
public class AutoLogHelperTest {

    /**
     * @since 0.0.3
     */
    @Test
    public void proxyTest() {
        UserService userService = AutoLogHelper.proxy(new UserServiceImpl());

        userService.queryLog("1");
    }

}
