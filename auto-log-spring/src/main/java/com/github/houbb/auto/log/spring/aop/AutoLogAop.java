package com.github.houbb.auto.log.spring.aop;

import com.github.houbb.aop.spring.util.SpringAopUtil;
import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.auto.log.core.bs.AutoLogBs;
import com.github.houbb.auto.log.core.support.sample.AlwaysTrueAutoLogSampleCondition;
import com.github.houbb.auto.log.spring.context.SpringAopAutoLogContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * 这是一种写法
 * 自动日志输出 aop
 * @author binbin.hou
 * @since 0.0.3
 */
//@Aspect
//@Component
//@EnableAspectJAutoProxy
@Deprecated
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
     *
     * // 匹配任意public方法
     * execution(public * *(..))
     * // 匹配任意以set开头的方法
     * execution(* set*(..))
     * // 匹配AccountService接口中定义的任意方法
     * execution(* com.xyz.service.AccountService.*(..))
     * // 匹配service包定义的任意方法
     * execution(* com.xyz.service.*.*(..))
     * // 匹配service或其子包中定义的任意方法
     * execution(* com.xyz.service..*.*(..))
     *
     */
    @Pointcut("@within(com.github.houbb.auto.log.annotation.AutoLog)" +
            "|| @annotation(com.github.houbb.auto.log.annotation.AutoLog)")
    public void autoLogPointcut() {
    }

    /**
     * 执行核心方法
     *
     * 相当于 MethodInterceptor
     *
     * @param point 切点
     * @return 结果
     * @throws Throwable 异常信息
     * @since 0.0.3
     */
    @Around("autoLogPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Method method = SpringAopUtil.getCurrentMethod(point);
        AutoLog autoLog = AnnotationUtils.getAnnotation(method, AutoLog.class);

        //获取当前类注解信息
        if(autoLog == null) {
            autoLog = SpringAopUtil.getClassAnnotation(point, AutoLog.class);
        }

        // 如果不存在
        if(autoLog == null) {
            return point.proceed();
        }
        // 如果存在，则执行切面的逻辑
        SpringAopAutoLogContext logContext = SpringAopAutoLogContext.newInstance();
        logContext.method(method)
                .autoLog(autoLog)
                .params(point.getArgs());
        logContext.point(point);

        //TODO 采样
        logContext.sampleCondition(new AlwaysTrueAutoLogSampleCondition());

        return AutoLogBs.newInstance()
                .context(logContext)
                .execute();
    }

}
