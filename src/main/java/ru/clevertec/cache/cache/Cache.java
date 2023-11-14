package ru.clevertec.cache.cache;

import java.util.List;

public interface Cache {

    boolean put(Long key, Object value);

    Object get(Long key);

    List<Object> getAll();

    boolean delete(Long key);
}
