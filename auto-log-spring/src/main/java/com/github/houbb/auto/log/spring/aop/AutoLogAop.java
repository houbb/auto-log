package com.github.houbb.auto.log.spring.aop;

import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.annotation.TraceId;
import com.github.houbb.auto.log.api.IAutoLogContext;
import com.github.houbb.auto.log.core.bs.AutoLogBs;
import com.github.houbb.auto.log.spring.context.SpringAopAutoLogContext;
import com.github.houbb.heaven.annotation.CommonEager;
import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 这是一种写法
 * 自动日志输出 aop
 * @author binbin.hou
 * @since 0.0.3
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class AutoLogAop {

    /**
     *
     * 切面方法：
     *
     * （1）扫描所有的共有方法
     * <pre>
     *     execution(public * *(..))
     * </pre>
     *
     * 问题：切面太大，废弃。
     * 使用扫描注解的方式替代。
     *
     * （2）扫描指定注解的方式
     *
     * 其实可以在 aop 中直接获取到注解信息，暂时先不调整。
     * 暂时先不添加 public 的限定
     *
     * （3）直接改成注解的优缺点：
     * 优点：减少了 aop 的切面访问
     * 缺点：弱化了注解的特性，本来是只要是 {@link com.github.houbb.auto.log.annotation.AutoLog} 指定的注解即可，
     *
     * 不过考虑到使用者的熟练度，如果用户知道了自定义注解，自定义 aop 应该也不是问题。
     */
    @Pointcut("@within(com.github.houbb.auto.log.annotation.AutoLog)" +
            "|| @within(com.github.houbb.auto.log.annotation.TraceId)" +
            "|| @annotation(com.github.houbb.auto.log.annotation.AutoLog)" +
            "|| @annotation(com.github.houbb.auto.log.annotation.TraceId)")
    public void autoLogPointcut() {
    }

    /**
     * 执行核心方法
     *
     * 相当于 MethodInterceptor
     *
     * 优先级：方法 > 类
     *
     * @param point 切点
     * @return 结果
     * @throws Throwable 异常信息
     * @since 0.0.3
     */
    @Around("autoLogPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Method method = getCurrentMethod(point);
        AutoLog autoLog = AnnotationUtils.getAnnotation(method, AutoLog.class);
        TraceId traceId = AnnotationUtils.getAnnotation(method, TraceId.class);

        //获取当前类注解信息
        if(autoLog == null) {
            autoLog = getClassAnnotation(point, AutoLog.class);
        }
        if(traceId == null) {
            traceId = getClassAnnotation(point, TraceId.class);
        }

        IAutoLogContext logContext = SpringAopAutoLogContext.newInstance()
                .method(method)
                .autoLog(autoLog)
                .traceId(traceId)
                .point(point);

        return AutoLogBs.newInstance()
                .context(logContext)
                .execute();
    }

    /**
     * 获取当前方法信息
     *
     * @param point 切点
     * @return 方法
     * @since 0.0.7
     */
    @CommonEager
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

    /**
     * 获取当前类指定的注解
     *
     * @param point 切点
     * @return 方法
     * @since 0.0.10
     */
    @CommonEager
    private <A extends Annotation> A getClassAnnotation(ProceedingJoinPoint point, Class<A> annotationType) {
        Class<?> targetClass = point.getTarget().getClass();
        return targetClass.getAnnotation(annotationType);
    }

}
