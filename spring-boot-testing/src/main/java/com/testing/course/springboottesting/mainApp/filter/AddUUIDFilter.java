package com.testing.course.springboottesting.mainApp.filter;

import org.slf4j.MDC;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@WebFilter("/*") // Add to all
public class AddUUIDFilter implements Filter {

    private static final String UUID_KEY = "requestUUID";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Add if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Generate a UUID for the request
        String uuid = UUID.randomUUID().toString();

        // Add the UUID to MDC so it can be used in logging
        MDC.put(UUID_KEY, uuid);

        try {
            // Proceed with the rest of the filter chain
            chain.doFilter(request, response);
        } finally {
            // Clean up MDC to avoid memory leaks
            MDC.remove(UUID_KEY);
        }
    }

    @Override
    public void destroy() {
        // Cleanup
    }
}