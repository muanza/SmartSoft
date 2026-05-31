package com.smartsoft.config;

import com.smartsoft.util.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TenantAuditInterceptor());
    }

    static class TenantAuditInterceptor implements HandlerInterceptor {
        private static final Logger LOG = LoggerFactory.getLogger(TenantAuditInterceptor.class);

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            String tenantId = request.getHeader("X-Tenant-ID");
            if (tenantId != null && !tenantId.isBlank()) {
                TenantContext.setCurrentTenant(tenantId);
            }
            LOG.info("AUDIT request method={} path={} tenant={}", request.getMethod(), request.getRequestURI(), TenantContext.getCurrentTenant());
            return true;
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
            TenantContext.clear();
        }
    }
}
