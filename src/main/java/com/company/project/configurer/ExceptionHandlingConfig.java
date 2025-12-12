package com.company.project.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ExceptionHandlingConfig implements WebMvcConfigurer {

    // 可以在这里添加额外的配置
    // 比如自定义错误视图映射等
}