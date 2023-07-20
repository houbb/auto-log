package com.github.houbb.auto.log.spring;


import com.github.houbb.auto.log.test.config.SpringConfig;
import com.github.houbb.auto.log.test.service.UserService;
import com.github.houbb.auto.log.test.service.impl.PrivateUserServiceImpl;
import com.github.houbb.heaven.util.lang.reflect.ReflectMethodUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author binbin.hou
 * @since 0.0.3
 */
@ContextConfiguration(classes = SpringConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringPrivateServiceTest {

    @Autowired
    private PrivateUserServiceImpl privateUserService;

    @Test
    public void queryLogTest() {
        privateUserService.callPrivate();
    }

}
