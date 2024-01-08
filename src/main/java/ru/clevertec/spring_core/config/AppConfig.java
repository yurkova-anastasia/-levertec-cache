package ru.clevertec.spring_core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import ru.clevertec.spring_core.cache.Cache;

@Configuration
@ComponentScan("ru.clevertec")
@PropertySource("classpath:application.yml")
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean
    public Cache cache() {
        Cache cache = CacheProvider.createCache();
        return cache;
    }
}