package com.github.houbb.auto.log.test.service;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public interface UserService {

    String query(final String id);

    String queryLog(final String id);

    /**
     * 慢日志
     * @param id 标识
     * @return 结果
     * @since 0.0.4
     */
    String slowLog(final String id);

    /**
     * @since 0.0.8
     * @param id id
     * @return 结果
     */
    String traceId(String id);

}
