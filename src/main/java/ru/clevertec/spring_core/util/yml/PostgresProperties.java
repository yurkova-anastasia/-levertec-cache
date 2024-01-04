package ru.clevertec.spring_core.util.yml;

import lombok.Data;

@Data
public class PostgresProperties {

    private String driver;
    private String url;
    private String user;
    private String password;
}
