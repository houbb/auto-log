# auto-log

[auto-log](https://github.com/houbb/auto-log) 是一款为 java 设计的自动日志监控框架。

[![Build Status](https://travis-ci.com/houbb/auto-log.svg?branch=master)](https://travis-ci.com/houbb/auto-log)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/auto-log/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/auto-log)
[![](https://img.shields.io/badge/license-Apache2-FF0080.svg)](https://github.com/houbb/auto-log/blob/master/LICENSE.txt)
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/houbb/auto-log)

## 创作目的

经常会写一些工具，有时候手动加一些日志很麻烦，引入 spring 又过于大材小用。

所以希望从从简到繁实现一个工具，便于平时使用。

## 特性

- 基于注解+字节码，配置灵活

- 自动适配常见的日志框架

- 支持编程式的调用

- 支持注解式，完美整合 spring

- 支持整合 spring-boot

- 支持慢日志阈值指定，耗时，入参，出参，异常信息等常见属性指定

- 支持 traceId 特性

- 支持类级别定义注解

- 支持自定义拦截器和过滤器

## 变更日志

> [变更日志](https://github.com/houbb/auto-log/blob/master/CHANGELOG.md)

# 快速开始

## maven 引入

```xml
<dependency>
    <group>com.github.houbb</group>
    <artifact>auto-log-core</artifact>
    <version>0.2.0</version>
</dependency>
```

## 入门案例

```java
UserService userService = AutoLogHelper.proxy(new UserServiceImpl());
userService.queryLog("1");
```

- 日志如下

```
[INFO] [2020-05-29 16:24:06.227] [main] [c.g.h.a.l.c.s.i.AutoLogMethodInterceptor.invoke] - public java.lang.String com.github.houbb.auto.log.test.service.impl.UserServiceImpl.queryLog(java.lang.String) param is [1]
[INFO] [2020-05-29 16:24:06.228] [main] [c.g.h.a.l.c.s.i.AutoLogMethodInterceptor.invoke] - public java.lang.String com.github.houbb.auto.log.test.service.impl.UserServiceImpl.queryLog(java.lang.String) result is result-1
```

### 代码

其中方法实现如下：

- UserService.java

```java
public interface UserService {

    String queryLog(final String id);

}
```

- UserServiceImpl.java

直接使用注解 `@AutoLog` 指定需要打日志的方法即可。

```java
public class UserServiceImpl implements UserService {

    @Override
    @AutoLog
    public String queryLog(String id) {
        return "result-"+id;
    }

}
```

## TraceId 的例子

### 代码

```java
UserService service =  AutoLogProxy.getProxy(new UserServiceImpl());
service.traceId("1");
```

其中 traceId 方法如下：

```java
@AutoLog
public String traceId(String id) {
    return id+"-1";
}
```

### 测试效果

```
信息: [ba7ddaded5a644e5a58fbd276b6657af] <traceId>入参: [1].
信息: [ba7ddaded5a644e5a58fbd276b6657af] <traceId>出参：1-1.
```

其中 ba7ddaded5a644e5a58fbd276b6657af 就是对应的 traceId，可以贯穿整个 thread 周期，便于我们日志查看。

# 注解说明

## @AutoLog

核心注解 `@AutoLog` 的属性说明如下：

| 属性 | 类型 | 默认值 | 说明 |
|:--|:--|:--|:--|
| enable | boolean | true | 是否启用 |
| param | boolean | true | 是否打印入参 |
| result | boolean | true | 是否打印出参 |
| costTime | boolean | false | 是否打印耗时 |
| exception | boolean | true | 是否打印异常 |
| slowThresholdMills | long | -1 | 当这个值大于等于 0 时，且耗时超过配置值，会输出慢日志 |
| description | string |"" | 方法描述，默认选择方法名称 |
| interceptor | Class[] | 默认实现 | 拦截器实现，支持指定多个和自定义 |
| paramFilter | Class | WebParamFilter | 入参过滤器，支持自定义 |
| traceId | Class | Id.class | 日志跟踪号生成策略 |
| enableTraceId | boolean | true | 是否启用 traceId 的变化 |

使用建议，在入口的方法中设置 `enableTraceId=true`，会统一设置 traceId，贯穿整个日志周期。 底层依赖的 service/biz 层等，设置为 false 即可。

# 自定义策略

## 自定义日志拦截器（interceptor）

### 内置拦截器

`AutoLogInterceptor` 默认实现

### 定义

直接继承自 `AbstractAutoLogInterceptor` 类，并且实现对应的方法即可。

```java
public class MyAutoLogInterceptor extends AbstractAutoLogInterceptor {

    @Override
    protected void doBefore(AutoLog autoLog, IAutoLogInterceptorContext context) {
        System.out.println("自定义入参：" + Arrays.toString(context.filterParams()));
    }

    @Override
    protected void doAfter(AutoLog autoLog, Object result, IAutoLogInterceptorContext context) {
        System.out.println("自定义出参：" + result);
    }

    @Override
    protected void doException(AutoLog autoLog, Exception exception, IAutoLogInterceptorContext context) {
        System.out.println("自定义异常：");
        exception.printStackTrace();
    }

}
```

### 使用

如下，这样日志输出，就会使用上面的指定策略。

```java
@AutoLog(interceptor = MyAutoLogInterceptor.class)
public String my() {
    return "自定义策略";
}
```

## 自定义入参过滤器（paramFilter）

### 内置

`WebParamFilter` 主要用于过滤 HttpRequest HttpServlet 等无法直接 JSON 序列化的对象。

### 自定义

直接继承 `AbstractParamFilter` 类实现对应的方法即可。

```java
public class MyParamFilter extends AbstractParamFilter {

    @Override
    protected Object[] doFilter(Object[] params) {
        Object[] newParams = new Object[1];
        newParams[0] = "设置我我想要的值";
        return newParams;
    }

}
```

### 使用

指定对应的参数过滤器。这样，无论入参是什么，都会变成我们指定的 `[设置我我想要的值]`。

```java
@AutoLog(paramFilter = MyParamFilter.class)
public String paramFilter() {
    return "自定义入参过滤器";
}
```


# spring 整合使用

完整示例参考 [SpringServiceTest](https://github.com/houbb/auto-log/tree/master/auto-log-test/src/test/java/com/github/houbb/auto/log/spring/SpringServiceTest.java)

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

# springboot 整合使用

## maven 引入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>auto-log-springboot-starter</artifactId>
    <version>最新版本</version>
</dependency>
```

只需要引入 jar 即可，其他的什么都不用配置。

使用方式和 spring 一致。

## 测试

```java
@Autowired
private UserService userService;

@Test
public void queryLogTest() {
    userService.query("spring-boot");
}
```

# 开源地址

> Github: [https://github.com/houbb/auto-log](https://github.com/houbb/auto-log)

> Gitee: [https://gitee.com/houbinbin/auto-log](https://gitee.com/houbinbin/auto-log)

# Road-Map

- [ ] 优化日志中的方法路径名称

考虑补全对应的类信息

- [ ] 全局配置

比如全局的慢日志阈值设置等

参考 sandglass 中如何加载注解中的配置信息？

- [ ] jvm-sandbox 特性

- [ ] 编译时注解特性 

# 开源矩阵

| 编号 | 名称 | 简介 | 标签 |
|:----|:----|:----|:----|
| 1 | [sensitive](https://github.com/houbb/sensitive) | 基于注解的日志脱敏框架，更加优雅的日志打印 | 工具，日志 |
| 2 | [auto-log](https://github.com/houbb/auto-log) | 日志自动输出 | 工具，日志 |
| 3 | [heaven](https://github.com/houbb/heaven) | 收集开发中常用的工具类 | 工具 |
| 4 | [resubmit](https://github.com/houbb/resubmit) | 防止重复提交框架 | 工具 |
| 5 | [validator](https://github.com/houbb/validator) | 新一代校验框架 | 工具 |
| 6 | [rate-limit](https://github.com/houbb/rate-limit) | 渐进式限流工具框架 | 工具 |
| 7 | [lock](https://github.com/houbb/lock) | 开箱即用分布式锁 | 工具 |
| 8 | [lombok-ex](https://github.com/houbb/lombok-ex) | 编译时注解框架，扩展 lombok | 工具 |
| 9 | [csv](https://github.com/houbb/csv) | CSV的读写工具 | 工具 |
| 10 | [iexcel](https://github.com/houbb/iexcel) | EXCEL的读写工具，避免OOM | 工具 |
| 11 | [leetcode](https://github.com/houbb/leetcode) | 力扣算法个人学习笔记 | 学习 |
| 12 | [awesome-metaverse-zh](https://github.com/houbb/awesome-metaverse-zh) | 元宇宙精选 | 学习 |
| 13 | [rpc](https://github.com/houbb/rpc) | 手写rpc框架 | 学习,中间件 |
| 14 | [mybatis](https://github.com/houbb/mybatis) | 手写mybatis框架 | 学习,中间件 |
| 15 | [cache](https://github.com/houbb/cache) | 手写redis框架 | 学习,中间件 |
| 16 | [mq](https://github.com/houbb/mq) | 手写mq框架 | 学习,中间件 |
| 17 | [ioc](https://github.com/houbb/ioc) | 手写spring ioc框架 | 学习,中间件 |
| 18 | [async](https://github.com/houbb/async) | 手写线程池异步框架 | 学习,中间件 |
| 19 | [jdbc-pool](https://github.com/houbb/jdbc-pool) | 手写数据库连接池实现 | 学习,中间件 |
| 20 | [sisyphus](https://github.com/houbb/sisyphus) | 支持注解的重试框架 | 学习,中间件 |
| 21 | [sandglass](https://github.com/houbb/sandglass) | 任务调度时间框架 | 学习,中间件 |
| 22 | [segment](https://github.com/houbb/segment) | 基于结巴的分词实现 | NLP |
| 23 | [pinyin](https://github.com/houbb/pinyin) | 高性能中文转拼音工具 | NLP |
| 24 | [opencc4j](https://github.com/houbb/opencc4j) | 中文繁简体转换 | NLP |
| 25 | [word-checker](https://github.com/houbb/word-checker) | 中英文拼写检测 | NLP |
| 26 | [sensitive-word](https://github.com/houbb/sensitive-word) | 敏感词 | NLP |
| 27 | [nlp-hanzi-similar](https://github.com/houbb/nlp-hanzi-similar) | 汉字相似度 | NLP |
| 28 | [word-cloud](https://github.com/houbb/word-cloud) | 好用的词云工具 | DOC |
| 29 | [markdown-toc](https://github.com/houbb/markdown-toc) | 为文档生成目录 | DOC |
| 30 | [idoc](https://github.com/houbb/idoc) | 项目自动生成文档 | DOC |
| 31 | [metadata](https://github.com/houbb/metadata) | 数据库元数据表文档生成 | DOC |
| 32 | [data-factory](https://github.com/houbb/data-factory) | 测试自动生成对象信息 | TEST |
| 33 | [junitperf](https://github.com/houbb/junitperf) | 性能测试框架，测试报告生成 | TEST |
| 34 | [houbb.github.io](https://github.com/houbb/houbb.github.io) | 个人博客 | 学习 |
