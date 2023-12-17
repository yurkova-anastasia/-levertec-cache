package ru.clevertec.cache.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.clevertec.cache.config.AppConfig;

import javax.sql.DataSource;
import java.sql.Connection;

@WebListener
public class FillDatabaseListener implements ServletContextListener {

    private DataSource dataSource;

    public FillDatabaseListener() {
        this.dataSource = AppConfig.getDataSource();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try(Connection connection = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            try (Liquibase liquibase =
                         new liquibase.Liquibase("databases/users/changelog.xml",
                                 new ClassLoaderResourceAccessor(), database)){
                liquibase.update(new Contexts(), new LabelExpression());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
