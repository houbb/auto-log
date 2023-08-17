package com.github.houbb.auto.log.spring.context;

import com.github.houbb.auto.log.core.core.impl.AbstractAutoLogContext;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author binbin.hou
 * @since 0.0.7
 */
public class SpringAopAutoLogContext extends AbstractAutoLogContext {

    /**
     * 切面信息
     * @since 0.0.7
     */
    private ProceedingJoinPoint point;

    /**
     * 新建对象实例
     * @return 实例
     * @since 0.0.7
     */
    public static SpringAopAutoLogContext newInstance() {
        return new SpringAopAutoLogContext();
    }

    public SpringAopAutoLogContext point(ProceedingJoinPoint point) {
        this.point = point;
        return this;
    }

    @Override
    public Object process() throws Throwable {
        return point.proceed();
    }

}
