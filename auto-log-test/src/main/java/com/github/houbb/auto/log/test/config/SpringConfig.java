package com.github.houbb.auto.log.test.config;


import com.github.houbb.auto.log.core.core.impl.SimpleAutoLog;
import com.github.houbb.auto.log.spring.annotation.EnableAutoLog;
import com.github.houbb.auto.log.test.autoLog.MyAutoLog;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author binbin.hou
 * @since 0.0.3
 */
@Configurable
@ComponentScan(basePackages = "com.github.houbb.auto.log.test.service")
@EnableAutoLog(value = {MyAutoLog.class, SimpleAutoLog.class})
public class SpringConfig {
}
