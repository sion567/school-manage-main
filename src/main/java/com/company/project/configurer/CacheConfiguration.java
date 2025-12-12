package com.company.project.configurer;


import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import com.github.benmanes.caffeine.cache.Caffeine;


@Configuration(proxyBeanMethods = false)
@EnableCaching
class CacheConfiguration {

    /*
    * 通过JCacheManagerCustomizer自定义JCache（JSR-107）缓存管理器
    * */
//    @Bean
//    public JCacheManagerCustomizer petclinicCacheConfigurationCustomizer() {
//        return cm -> cm.createCache("vets", cacheConfiguration());
//    }

    private javax.cache.configuration.Configuration<Object, Object> cacheConfiguration() {
        return new MutableConfiguration<>()
                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.THIRTY_MINUTES))
                .setStoreByValue(false)
                .setStatisticsEnabled(true);
    }

    /**
     * 通过@EnableCaching注解启用缓存后，Spring会自动注入CacheManager，用于处理@Cacheable等注解的缓存操作
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("defaultCache");
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .expireAfterWrite(600, TimeUnit.SECONDS) // 缓存过期时间
                .maximumSize(1000); // 最大缓存条数
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

}

