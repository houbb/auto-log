package com.github.houbb.auto.log.test.dynamic;


import com.github.houbb.auto.log.spring.annotation.EnableAutoLog;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @author binbin.hou
 * @since 0.0.3
 */
@Configurable
@ComponentScan(basePackages = "com.github.houbb.auto.log.test.dynamic.service")
@EnableAutoLog
@PropertySource("classpath:autoLogConfig.properties")
public class SpringDynamicConfig {
}
