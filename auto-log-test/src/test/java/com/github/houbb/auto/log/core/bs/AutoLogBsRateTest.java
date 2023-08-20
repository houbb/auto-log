package com.github.houbb.auto.log.core.bs;

import com.github.houbb.auto.log.core.support.proxy.AutoLogProxy;
import com.github.houbb.auto.log.test.service.impl.*;
import org.junit.Test;

/**
 * @author binbin.hou
 * @since 0.5.0
 */
public class AutoLogBsRateTest {

    @Test
    public void rate30Test() {
        SampleRateServiceImpl userService = AutoLogProxy.getProxy(new SampleRateServiceImpl());
        for(int i = 0; i < 10; i++ ) {
            userService.sample30Test(i);
        }
    }

    @Test
    public void rate50Test() {
        SampleRateServiceImpl userService = AutoLogProxy.getProxy(new SampleRateServiceImpl());
        for(int i = 0; i < 10; i++ ) {
            userService.sample50Test(i);
        }
    }

    @Test
    public void rate100Test() {
        SampleRateServiceImpl userService = AutoLogProxy.getProxy(new SampleRateServiceImpl());
        for(int i = 0; i < 10; i++ ) {
            userService.sample100Test(i);
        }
    }

    @Test
    public void rate0Test() {
        SampleRateServiceImpl userService = AutoLogProxy.getProxy(new SampleRateServiceImpl());
        for(int i = 0; i < 10; i++ ) {
            userService.sample0Test(i);
        }
    }

}
