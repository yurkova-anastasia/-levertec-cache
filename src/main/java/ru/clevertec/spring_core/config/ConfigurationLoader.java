package ru.clevertec.spring_core.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigurationLoader {

    @Getter
    private static final AnnotationConfigApplicationContext configuration =
            new AnnotationConfigApplicationContext(AppConfig.class);
}