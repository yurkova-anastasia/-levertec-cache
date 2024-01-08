package ru.clevertec.spring_core.cache;

import java.util.List;

public interface Cache {

    boolean put(Long key, Object value);

    Object get(Long key);

    List<Object> getAll();

    boolean delete(Long key);
}
