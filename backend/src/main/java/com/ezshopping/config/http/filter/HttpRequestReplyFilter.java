package com.ezshopping.config.http.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class HttpRequestReplyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(!request.getRequestURI().contains("actuator")) {
            log.info("[{}] request for URL: [{}]", request.getMethod(), request.getRequestURI());
        }
        filterChain.doFilter(servletRequest, servletResponse);
        if(!request.getRequestURI().contains("actuator")) {
            log.info("Response status: [{}]", response.getStatus());
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
