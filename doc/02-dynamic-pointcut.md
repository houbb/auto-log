# 业务背景

很久以前开源了一款 [auto-log](https://github.com/houbb/auto-log) 自动日志打印框架。

其中对于 spring 项目，默认实现了基于 aop 切面的日志输出。

但是发现一个问题，如果切面定义为全切范围过大，于是 v0.2 版本就是基于注解 `@AutoLog` 实现的。

只有指定注解的类或者方法才会生效，但是这样使用起来很不方便。

如何才能动态指定 pointcut，让用户使用时可以自定义切面范围呢？

# 自定义注解切面原理

## 常规 aop 方式

```java
@Aspect
@Component
@EnableAspectJAutoProxy
@Deprecated
public class AutoLogAop {

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
        // 日志增强逻辑
    }

}
```

发现这里的 `@Pointcut` 注解属性是一个常量，无法方便地动态修改。

于是去查资料，找到了另一种更加灵活的方式。

## 可以指定 pointcut 的方式

我们通过 `@Value` 获取属性配置的切面值，给定默认值。这样用户就可以很方便的自定义。

```java
/**
 * 动态配置的切面
 * 自动日志输出 aop
 * @author binbin.hou
 * @since 0.3.0
 */
@Configuration
@Aspect
//@EnableAspectJAutoProxy
public class AutoLogDynamicPointcut {

    /**
     * 切面设置，直接和 spring 的配置对应 ${}，可以从 properties 或者配置中心读取。更加灵活
     */
    @Value("${auto.log.pointcut:@within(com.github.houbb.auto.log.annotation.AutoLog)||@annotation(com.github.houbb.auto.log.annotation.AutoLog)}")
    private String pointcut;

    @Bean("autoLogPointcutAdvisor")
    public AspectJExpressionPointcutAdvisor autoLogPointcutAdvisor() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(pointcut);
        advisor.setAdvice(new AutoLogAdvice());
        return advisor;
    }

}
```

当然，这里的 Advice 和以前的 aop 不同，需要重新进行实现。

### AutoLogAdvice

只需要实现 MethodInterceptor 接口即可。

```java
/**
 * 切面拦截器
 *
 * @author binbin.hou
 * @since 0.3.0
 */
public class AutoLogAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        // 增强逻辑
    }

}
```

介绍完了原理，我们一起来看下改进后的日志打印组件的效果。

# spring 整合使用

完整示例参考 [SpringServiceTest](https://github.com/houbb/auto-log/tree/master/auto-log-test/src/test/java/com/github/houbb/auto/log/spring/SpringServiceTest.java)

## maven 引入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>auto-log-spring</artifactId>
    <version>0.3.0</version>
</dependency>
```

## 注解声明

使用 `@EnableAutoLog` 启用自动日志输出

```java
@Configurable
@ComponentScan(basePackages = "com.github.houbb.auto.log.test.service")
@EnableAutoLog
public class SpringConfig {
}
```

## 测试代码

```java
@ContextConfiguration(classes = SpringConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void queryLogTest() {
        userService.queryLog("1");
    }

}
```

- 输出结果

```
信息: public java.lang.String com.github.houbb.auto.log.test.service.impl.UserServiceImpl.queryLog(java.lang.String) param is [1]
五月 30, 2020 12:17:51 下午 com.github.houbb.auto.log.core.support.interceptor.AutoLogMethodInterceptor info
信息: public java.lang.String com.github.houbb.auto.log.test.service.impl.UserServiceImpl.queryLog(java.lang.String) result is result-1
五月 30, 2020 12:17:51 下午 org.springframework.context.support.GenericApplicationContext doClose
```

## 切面自定义

### 原理解释

spring aop 的切面读取自 `@Value("${auto.log.pointcut}")`，默认为值 `@within(com.github.houbb.auto.log.annotation.AutoLog)||@annotation(com.github.houbb.auto.log.annotation.AutoLog)`

也就是默认是读取被 `@AutoLog` 指定的方法或者类。

当然，这并不够方便，我们希望可以想平时写 aop 注解一样，指定 spring aop 的扫描范围，直接在 spring 中指定一下 `auto.log.pointcut` 的属性值即可。

### 测试例子

> [完整测试代码](https://github.com/houbb/auto-log/blob/master/auto-log-test/src/test/java/com/github/houbb/auto/log/dynamic/SpringDynamicServiceTest.java)

我们在配置文件 `autoLogConfig.properties` 中自定义下包扫描的范围：

```
auto.log.pointcut=execution(* com.github.houbb.auto.log.test.dynamic.service.MyAddressService.*(..))
```

自定义测试 service

```java
package com.github.houbb.auto.log.test.dynamic.service;

import org.springframework.stereotype.Service;

@Service
public class MyAddressService {

    public String queryAddress(String id) {
        return "address-" + id;
    }

}
```

自定义 spring 配置，指定我们定义的配置文件。springboot 啥的，可以直接放在 application.properties 中指定，此处仅作为演示。

```java
@Configurable
@ComponentScan(basePackages = "com.github.houbb.auto.log.test.dynamic.service")
@EnableAutoLog
@PropertySource("classpath:autoLogConfig.properties")
public class SpringDynamicConfig {
}
```

测试

```java
@ContextConfiguration(classes = SpringDynamicConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringDynamicServiceTest {

    @Autowired
    private MyAddressService myAddressService;

    @Autowired
    private MyUserService myUserService;

    @Test
    public void queryUserTest() {
        // 不会被日志拦截
        myUserService.queryUser("1");
    }

    @Test
    public void queryAddressTest() {
        // 会被日志拦截
        myAddressService.queryAddress("1");
    }

}
```

# 开源地址

为了便于大家学习，项目已开源。

> Github: [https://github.com/houbb/auto-log](https://github.com/houbb/auto-log)

> Gitee: [https://gitee.com/houbinbin/auto-log](https://gitee.com/houbinbin/auto-log)

# 小结

这个项目很长一段时间拘泥于注解的方式，我个人用起来也不是很方便。

最近才想到了改进的方法，人还是要不断学习进步。

关于日志最近还学到了 aspect 的编译时增强，和基于 agent 的运行时增强，这 2 种方式都很有趣，有机会会做学习记录。