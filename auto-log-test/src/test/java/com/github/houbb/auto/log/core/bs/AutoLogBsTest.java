package com.github.houbb.auto.log.core.bs;

import com.github.houbb.auto.log.test.service.UserService;
import com.github.houbb.auto.log.test.service.impl.MenuServiceImpl;
import com.github.houbb.auto.log.test.service.impl.UserServiceImpl;
import org.junit.Test;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public class AutoLogBsTest {

    @Test
    public void proxyTest() {
        UserService userService = AutoLogBs.newInstance().proxy(new UserServiceImpl());

        userService.queryLog("1");
    }

    /**
     * 没有接口而测试
     * @since 0.0.5
     */
    @Test
    public void proxyTest2() {
        MenuServiceImpl service = AutoLogBs.newInstance().proxy(new MenuServiceImpl());

        service.query("1");
    }

}
