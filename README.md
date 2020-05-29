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

- 声明式编程

- 基于注解+字节码，配置灵活

- 自动适配常见的日志框架

> [变更日志](https://github.com/houbb/auto-log/blob/master/CHANGELOG.md)

# 快速开始

## maven 引入

```xml
<dependency>
    <group>com.github.houbb</group>
    <artifact>auto-log-core</artifact>
    <version>0.0.2</version>
</dependency>
```

## 入门案例

```java
UserService userService = AutoLogBs.proxy(new UserServiceImpl());
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
        return "result-"+id);
    }

}
```



# Road-Map

- [ ] 支持类声明注解

- [ ] spring 兼容
