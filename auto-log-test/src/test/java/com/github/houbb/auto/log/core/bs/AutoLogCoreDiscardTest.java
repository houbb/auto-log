package com.github.houbb.auto.log.core.bs;

import com.github.houbb.auto.log.core.support.proxy.AutoLogProxy;
import com.github.houbb.auto.log.test.service.DiscardService;
import com.github.houbb.auto.log.test.service.UserService;
import com.github.houbb.auto.log.test.service.impl.ClassAnnotationService;
import com.github.houbb.auto.log.test.service.impl.DefineService;
import com.github.houbb.auto.log.test.service.impl.MenuServiceImpl;
import com.github.houbb.auto.log.test.service.impl.UserServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author binbin.hou
 * @since 0.11.0
 */
public class AutoLogCoreDiscardTest {

    @Test
    public void discardTest() {
        DiscardService userService = AutoLogProxy.getProxy(new DiscardService());

        List<String> idList = new ArrayList<>();
        for(int i = 0; i < 10000; i++) {
            idList.add("id-" + i);
        }

        userService.queryByIds(idList);
    }

    @Test
    public void maxLenTest() {
        DiscardService userService = AutoLogProxy.getProxy(new DiscardService());
        userService.getString(10000);
    }

}
