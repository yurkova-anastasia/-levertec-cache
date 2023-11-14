package ru.clevertec.cache.config;

import lombok.experimental.UtilityClass;
import ru.clevertec.cache.cache.Cache;
import ru.clevertec.cache.cache.lfuCache.LFUCache;
import ru.clevertec.cache.cache.lruCache.LRUCache;

@UtilityClass
public class CacheConfig {
    private static String cacheType;

    private static int cacheMaxSize;

    static {
        cacheType = AppConfig.getYamlParser().getYaml().getCacheType();
        cacheMaxSize = AppConfig.getYamlParser().getYaml().getCacheMaxSize();
    }

    public Cache createCache() {
       return switch (cacheType) {
            case "LFU" -> new LFUCache(cacheMaxSize);
            case "LRU" -> new LRUCache(cacheMaxSize);
            default -> null;
        };
    }
}
