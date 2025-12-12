package com.company.project.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({H2ConsoleSecurityConfig.class, OpenApiConfig.class})
public class DeveSecurityConfig {
}
