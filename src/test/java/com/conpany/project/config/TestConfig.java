package com.conpany.project.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

@TestConfiguration
@ComponentScan(basePackages = {
        "com.company.project.core.dao",
        "com.company.project.dao",
        "com.company.project.service",
        "com.company.project.web"
})
public class TestConfig {

    @Bean
    @Primary
    public String testDatabaseUrl() {
        return "jdbc:h2:mem:testdb";
    }
}
