package ru.clevertec.cache.util;

import lombok.Data;

@Data
public class PostgresProperties {

    private String driver;
    private String url;
    private String user;
    private String password;

}
