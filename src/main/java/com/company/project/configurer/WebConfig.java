package com.company.project.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 明确注册视图控制器
        registry.addViewController("/").setViewName("forward:/login");
        registry.addViewController("/index").setViewName("forward:/login");
        registry.addViewController("/dashboard").setViewName("forward:/login");
    }
}