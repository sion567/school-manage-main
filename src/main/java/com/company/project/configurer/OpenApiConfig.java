package com.company.project.configurer;

import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
@ConditionalOnProperty(prefix = "springdoc.api-docs", name = "enabled", havingValue = "true")
@OpenAPIDefinition
public class OpenApiConfig {

    @Bean
    @Order(10)
    SecurityFilterChain openApiSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/swagger-ui.html","/swagger-ui/**", "/v3/api-docs/**")
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                        RegexRequestMatcher.regexMatcher("/swagger-ui/.*"),
                        RegexRequestMatcher.regexMatcher("/swagger-ui.html"),
                        RegexRequestMatcher.regexMatcher("/v3/api-docs/.*")
                ).permitAll());
        return http.build();
    }

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.info(new Info().title("API 文档").version("1.0"));
        // 配置 Authorization 登录鉴权
        Map<String, SecurityScheme> map = Map.of("Authorization",
                new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization"));
        openAPI.components(new Components().securitySchemes(map));
        map.keySet().forEach(key -> openAPI.addSecurityItem(new SecurityRequirement().addList(key)));
        return openAPI;
    }

}

