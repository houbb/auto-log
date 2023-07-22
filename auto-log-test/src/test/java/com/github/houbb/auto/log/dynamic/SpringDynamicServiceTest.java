package com.github.houbb.auto.log.dynamic;


import com.github.houbb.auto.log.test.dynamic.SpringDynamicConfig;
import com.github.houbb.auto.log.test.dynamic.service.MyAddressService;
import com.github.houbb.auto.log.test.dynamic.service.MyUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author binbin.hou
 * @since 0.3.0
 */
@ContextConfiguration(classes = SpringDynamicConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringDynamicServiceTest {

    @Autowired
    private MyAddressService myAddressService;

    @Autowired
    private MyUserService myUserService;

    @Test
    public void queryUserTest() {
        myUserService.queryUser("1");
    }

    @Test
    public void queryAddressTest() {
        myAddressService.queryAddress("1");
    }

}
