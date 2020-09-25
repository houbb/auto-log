package com.github.houbb.auto.log.test.autoLog;

import com.github.houbb.auto.log.core.core.IAutoLog;
import com.github.houbb.auto.log.core.core.IAutoLogContext;

import java.util.Arrays;

/**
 * 自定义实现
 * @author binbin.hou
 * @since 0.0.9
 */
public class MyAutoLog implements IAutoLog {

    @Override
    public Object autoLog(IAutoLogContext context) throws Throwable {
        System.out.println("自定义参数：" + Arrays.toString(context.params()));
        Object result = context.process();
        System.out.println("自定义结果：" + result);
        return result;
    }

}
