package ru.clevertec.spring_core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("ru.clevertec")
@PropertySource("classpath:application.properties")
public class AppConfig {

}