package com.github.houbb.auto.log.core.support.interceptor.web.server;

import com.github.houbb.auto.trace.interceptor.web.AbstractHttpServerInterceptor;
import com.github.houbb.auto.trace.model.Span;

import javax.servlet.*;
import java.io.IOException;

/**
 * 基于 servlet filter 的实现
 * @since 0.1.0
 */
public class AutoLogHttpServerServletFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            // 继续处理请求
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            //TODO...
        }
    }

    @Override
    public void destroy() {

    }

}
