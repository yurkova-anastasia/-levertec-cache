package ru.clevertec.cache.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import org.postgresql.ds.PGSimpleDataSource;
import ru.clevertec.cache.repository.UserRepository;
import ru.clevertec.cache.service.UserService;
import ru.clevertec.cache.service.impl.UserServiceImpl;
import ru.clevertec.cache.util.pdf.UserPdfPrinter;
import ru.clevertec.cache.util.pdf.impl.UserPdfPrinterImpl;
import ru.clevertec.cache.util.yml.YMLParser;

import javax.sql.DataSource;

/**
 * Configuration class responsible for initializing various components of the application.
 * This class sets up the data source, repositories, services, and other necessary objects used throughout the application.
 * It also provides access to commonly used instances such as the YAML parser and Jackson ObjectMapper.
 * All components are initialized as static fields for easy access throughout the application.
 *
 * @author Yurkova Anastacia
 */
@UtilityClass
public class AppConfig {

    private static final YMLParser yamlParser;
    private static final DataSource dataSource;
    private static final UserRepository userRepository;
    private static final UserService userService;
    private static final ObjectMapper objectMapper;
    private static final UserPdfPrinter userPdfPrinter;

    static {
        yamlParser = new YMLParser();

        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setURL(yamlParser.getYaml().getPostgres().getUrl());
        pgSimpleDataSource.setUser(yamlParser.getYaml().getPostgres().getUser());
        pgSimpleDataSource.setPassword(yamlParser.getYaml().getPostgres().getPassword());
        dataSource = pgSimpleDataSource;

        userRepository = new UserRepository(dataSource);

        userService = new UserServiceImpl(userRepository);

        objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());

        userPdfPrinter = new UserPdfPrinterImpl();

    }

    public static YMLParser getYamlParser() {
        return yamlParser;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static UserRepository getUserRepository() {
        return userRepository;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static ObjectMapper getObjectMapper() {return objectMapper;}

    public static UserPdfPrinter getUserPdfPrinter() {return userPdfPrinter;}
}
