package com.github.houbb.auto.log.spring.aop;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.core.bs.AutoLogBs;
import com.github.houbb.auto.log.core.support.sample.AlwaysTrueAutoLogSampleCondition;
import com.github.houbb.auto.log.spring.config.DefaultAutoLogGlobalConfig;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * 切面拦截器
 *
 * @author binbin.hou
 * @since 0.3.0
 */
public class AutoLogAdvice implements MethodInterceptor {

    private AutoLog getAutoLogAnnotation(final MethodInvocation methodInvocation) {
        //1. 方法级别
        Method method = methodInvocation.getMethod();
        AutoLog autoLog = AnnotationUtils.getAnnotation(method, AutoLog.class);
        if (autoLog != null) {
            return autoLog;
        }

        //2. 类级别
        final Class<?> targetClass = methodInvocation.getThis().getClass();
        autoLog = targetClass.getAnnotation(AutoLog.class);
        if (autoLog != null) {
            return autoLog;
        }

        //3. 默认的注解信息
        return DefaultAutoLogGlobalConfig.class.getAnnotation(AutoLog.class);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        AutoLog autoLog = getAutoLogAnnotation(methodInvocation);

        // 如果存在，则执行切面的逻辑
        AutoLogAdviceContext logContext = AutoLogAdviceContext.newInstance();
        logContext.methodInvocation(methodInvocation)
                .method(methodInvocation.getMethod())
                .autoLog(autoLog)
                .params(methodInvocation.getArguments())
                .sampleCondition(new AlwaysTrueAutoLogSampleCondition())
        ;

        return AutoLogBs.newInstance()
                .context(logContext)
                .execute();
    }

}
