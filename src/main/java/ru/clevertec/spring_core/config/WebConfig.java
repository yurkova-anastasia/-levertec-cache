package ru.clevertec.spring_core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.clevertec.spring_core.repository.UserRepository;
import ru.clevertec.spring_core.util.yml.YMLParser;

import javax.sql.DataSource;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"ru.clevertec.spring_core"})
public class WebConfig {

    @Bean
    public YMLParser ymlParser() {
        return new YMLParser();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setURL(ymlParser().getYaml().getPostgres().getUrl());
        pgSimpleDataSource.setUser(ymlParser().getYaml().getPostgres().getUser());
        pgSimpleDataSource.setPassword(ymlParser().getYaml().getPostgres().getPassword());
        return pgSimpleDataSource;
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository(dataSource());
    }

}