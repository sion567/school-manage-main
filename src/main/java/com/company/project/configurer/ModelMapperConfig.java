package com.company.project.configurer;

import com.company.project.core.model.BaseEntity;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.text.html.parser.Entity;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration()
                // 严格匹配策略 - 最严格的匹配方式
                .setMatchingStrategy(MatchingStrategies.STRICT)
                // 启用字段级别的匹配
                .setFieldMatchingEnabled(true)
                // 只有当源对象的属性不为 null 时才进行映射
//                .setPropertyCondition(Conditions.isNotNull())
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

//        configureGlobalSkipId(mapper);

        return mapper;
    }

//    private void configureGlobalSkipId(ModelMapper mapper) {
//        mapper.createTypeMap(BaseEntity.class, BaseEntity.class)
//                .addMapping()
//                .skip(BaseEntity::setId);  // 跳过ID字段
//    }
}
