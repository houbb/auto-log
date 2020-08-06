package com.github.houbb.auto.log.test2;

import com.github.houbb.auto.log.test2.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author binbin.hou
 * @since 0.0.5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AutoLogApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void queryLogTest() {
        userService.query("spring-boot");
    }

}
