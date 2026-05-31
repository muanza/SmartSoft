package com.smartsoft;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,"
                + "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration",
        "smartsoft.jpa.enabled=false"
})
class SmartSoftApplicationTests {

    @Test
    void contextLoads() {
    }
}
