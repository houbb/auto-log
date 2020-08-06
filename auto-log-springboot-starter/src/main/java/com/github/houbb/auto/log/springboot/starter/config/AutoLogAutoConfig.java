package com.github.houbb.auto.log.springboot.starter.config;

import com.github.houbb.auto.log.spring.annotation.EnableAutoLog;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * 防止重复提交自动配置
 * @author binbin.hou
 * @since 0.0.3
 */
@Configuration
@ConditionalOnClass(EnableAutoLog.class)
@EnableAutoLog
public class AutoLogAutoConfig {
}
