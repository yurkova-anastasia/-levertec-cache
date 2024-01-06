package ru.clevertec.spring_core.config;

import lombok.experimental.UtilityClass;
import ru.clevertec.spring_core.cache.Cache;
import ru.clevertec.spring_core.cache.lruCache.LRUCache;
import ru.clevertec.spring_core.cache.lfuCache.LFUCache;
import ru.clevertec.spring_core.util.yml.YMLParser;

@UtilityClass
public class CacheProvider {

    private static YMLParser yamlParser;

    private static String cacheType;

    private static int cacheMaxSize;

    static {
        yamlParser = new YMLParser();
        cacheType = yamlParser.getYaml().getCache().getType();
        cacheMaxSize = yamlParser.getYaml().getCache().getMaxSize();
    }
    public Cache createCache() {
       return switch (cacheType) {
            case "LFU" -> new LFUCache(cacheMaxSize);
            case "LRU" -> new LRUCache(cacheMaxSize);
            default -> null;
        };
    }
}
