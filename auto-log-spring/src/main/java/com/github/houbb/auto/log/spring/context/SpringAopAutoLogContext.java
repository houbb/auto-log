package com.github.houbb.auto.log.spring.context;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.annotation.TraceId;
import com.github.houbb.auto.log.core.core.IAutoLogContext;
import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author binbin.hou
 * @since 0.0.7
 */
public class SpringAopAutoLogContext implements IAutoLogContext {

    /**
     * 注解信息
     * @since 0.0.7
     */
    private AutoLog autoLog;

    /**
     * 日志标识注解
     * @since 0.0.8
     */
    private TraceId traceId;

    /**
     * 参数信息
     * @since 0.0.7
     */
    private Object[] params;

    /**
     * 方法信息
     * @since 0.0.7
     */
    private Method method;

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

    @Override
    public AutoLog autoLog() {
        return autoLog;
    }

    public SpringAopAutoLogContext autoLog(AutoLog autoLog) {
        this.autoLog = autoLog;
        return this;
    }

    @Override
    public TraceId traceId() {
        return traceId;
    }

    public SpringAopAutoLogContext traceId(TraceId traceId) {
        this.traceId = traceId;
        return this;
    }

    @Override
    public Object[] params() {
        return params;
    }

    @Override
    public Method method() {
        return method;
    }

    public SpringAopAutoLogContext point(ProceedingJoinPoint point) {
        this.point = point;
        this.method = getCurrentMethod(point);
        this.params = point.getArgs();
        return this;
    }

    @Override
    public Object process() throws Throwable {
        return point.proceed();
    }

    /**
     * 获取当前方法信息
     *
     * @param point 切点
     * @return 方法
     * @since 0.0.7
     */
    private Method getCurrentMethod(ProceedingJoinPoint point) {
        try {
            Signature sig = point.getSignature();
            MethodSignature msig = (MethodSignature) sig;
            Object target = point.getTarget();
            return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new CommonRuntimeException(e);
        }
    }

}
