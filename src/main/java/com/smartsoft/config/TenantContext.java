package com.smartsoft.config;

public class TenantContext {

    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    private TenantContext() {}

    public static String getCurrentTenant() {
        return CURRENT_TENANT.get();
    }

    public static void setCurrentTenant(String tenantNif) {
        CURRENT_TENANT.set(tenantNif);
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}
