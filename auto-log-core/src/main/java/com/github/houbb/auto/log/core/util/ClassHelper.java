package com.github.houbb.auto.log.core.util;

import com.github.houbb.heaven.annotation.CommonEager;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;

import java.util.List;

/**
 * 类工具类
 * @author binbin.hou
 * @since 0.0.11
 */
@CommonEager
public final class ClassHelper {

    private ClassHelper(){}

    /**
     * 是否是指定接口的子类
     *
     * 目的：不引入接口的情况下，做关系的判断
     *
     * TODO: 拓展，添加一下父类的判断
     * @param clazz 类
     * @param interfaceName 接口名称
     * @return 结果
     * @since 0.0.11
     */
    public static boolean instanceOf(final Class<?> clazz,
                                     final String interfaceName) {
        ArgUtil.notNull(clazz, "clazz");
        ArgUtil.notEmpty(interfaceName, "interfaceName");

        // 就是自己
        if(clazz.getName().equals(interfaceName)) {
            return true;
        }

        List<Class> interfaces = ClassUtil.getAllInterfaces(clazz);
        if(CollectionUtil.isEmpty(interfaces)) {
            return false;
        }

        for(Class<?> clazzInter : interfaces) {
            String name = clazzInter.getName();
            if(name.equals(interfaceName)) {
                return true;
            }
        }
        return false;
    }

}
