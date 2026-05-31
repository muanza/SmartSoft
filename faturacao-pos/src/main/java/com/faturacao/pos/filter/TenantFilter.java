package com.faturacao.pos.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class TenantFilter implements Filter {

    private static final String TENANT_PATTERN = "\\d{9,20}";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);
        String tenant = req.getParameter("tenantId");
        if (session != null && session.getAttribute("posUser") != null && tenant != null && tenant.matches(TENANT_PATTERN)) {
            Object tenantSessao = session.getAttribute("tenantId");
            if (tenantSessao == null || tenant.equals(tenantSessao.toString())) {
                session.setAttribute("tenantId", tenant);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
