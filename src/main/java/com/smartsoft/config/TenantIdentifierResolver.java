package com.smartsoft.config;

import com.smartsoft.util.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    private static final String DEFAULT_TENANT = "public";

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = TenantContext.getCurrentTenant();
        return tenant == null || tenant.isBlank() ? DEFAULT_TENANT : tenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
