package ru.clevertec.spring_core.util.yml;

import lombok.Data;

@Data
public class CacheProperties {

    private String type;
    private int maxSize;

}
