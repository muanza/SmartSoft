package com.smartsoft.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ConditionalOnProperty(name = "smartsoft.jpa.enabled", havingValue = "true", matchIfMissing = true)
@EnableTransactionManagement
@EntityScan(basePackages = "com.smartsoft.entity")
@EnableJpaRepositories(basePackages = "com.smartsoft.repository")
public class JpaConfig {
}
