package com.faturacao.pos.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TenantFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String tenant = req.getParameter("tenantId");
        if (tenant != null && !tenant.isBlank()) {
            req.getSession(true).setAttribute("tenantId", tenant);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
