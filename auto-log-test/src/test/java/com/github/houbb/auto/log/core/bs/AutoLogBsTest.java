package com.github.houbb.auto.log.core.bs;

import com.github.houbb.auto.log.core.support.proxy.AutoLogProxy;
import com.github.houbb.auto.log.test.service.UserService;
import com.github.houbb.auto.log.test.service.impl.ClassAnnotationService;
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
        UserService userService = AutoLogProxy.getProxy(new UserServiceImpl());

        userService.queryLog("1");
    }

    /**
     * 没有接口而测试
     * @since 0.0.5
     */
    @Test
    public void proxyTest2() {
        MenuServiceImpl service =  AutoLogProxy.getProxy(new MenuServiceImpl());

        service.query("1");
    }

    /**
     * 接口测试 TraceId
     * @since 0.0.8
     */
    @Test
    public void proxyTestTraceId() {
        UserService service =  AutoLogProxy.getProxy(new UserServiceImpl());

        service.traceId("1");
    }

    @Test
    public void classEnableTest() {
        ClassAnnotationService annotationService = AutoLogProxy.getProxy(new ClassAnnotationService());
        annotationService.enable();
    }

    @Test
    public void classDisableTest() {
        ClassAnnotationService annotationService = AutoLogProxy.getProxy(new ClassAnnotationService());
        annotationService.disable();
    }

}
