package ru.clevertec.spring_core.connection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.postgresql.ds.PGSimpleDataSource;
import ru.clevertec.spring_core.util.pdf.yml.YMLParser;

import javax.sql.DataSource;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataSourceProvider {

    private static DataSource dataSource;
    private static YMLParser yamlParser;

    static {
        yamlParser = new YMLParser();
    }

    public static DataSource getDataSource() {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setURL(yamlParser.getYaml().getPostgres().getUrl());
        pgSimpleDataSource.setUser(yamlParser.getYaml().getPostgres().getUser());
        pgSimpleDataSource.setPassword(yamlParser.getYaml().getPostgres().getPassword());
        dataSource = pgSimpleDataSource;
        return dataSource;
    }
}
