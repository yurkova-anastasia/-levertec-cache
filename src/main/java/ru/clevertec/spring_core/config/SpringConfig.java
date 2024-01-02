package ru.clevertec.spring_core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.spring_core.repository.UserRepository;
import ru.clevertec.spring_core.service.UserService;
import ru.clevertec.spring_core.service.impl.UserServiceImpl;
import ru.clevertec.spring_core.servlets.UserServlet;
import ru.clevertec.spring_core.util.yml.YMLParser;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"ru.clevertec.spring_core"})
public class SpringConfig {

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
    public UserServlet userServlet() {
        return new UserServlet();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository(dataSource());
    }

}