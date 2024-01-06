package ru.clevertec.spring_core.cache.lfuCache;

import lombok.Data;
import ru.clevertec.spring_core.cache.Cache;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LFUCache implements Cache {

    private int size;
    private Map<Long, CacheElement> cache;

    public LFUCache(int size) {
        this.size = size;
        this.cache = new HashMap<>(size);
    }

    @Override
    public boolean put(Long key, Object value) {
        CacheElement element = new CacheElement(0, value);
        if (cache.size() >= this.size) {
            evictElement();
        }
        cache.put(key, element);
        return true;
    }

    @Override
    public Object get(Long key) {
        if (cache.containsKey(key)) {
            CacheElement element = cache.get(key);
            element.setNumberOfRequests(element.getNumberOfRequests() + 1);
            return element.getValue();
        } else {
            return null;
        }
    }

    @Override
    public List<Object> getAll() {
        Collection<CacheElement> cacheElements = cache.values();
        cacheElements = cacheElements.stream()
                .peek(c -> c.setNumberOfRequests(c.getNumberOfRequests() + 1))
                .toList();
        List<Object> listObjects = cacheElements.stream()
                .map(CacheElement::getValue)
                .toList();
        return listObjects;
    }

    @Override
    public boolean delete(Long key) {
        CacheElement removed = cache.remove(key);
        return removed != null;
    }


    private void evictElement() {
        CacheElement elementToRemove = cache.values().stream()
                .min(Comparator.comparing(CacheElement::getNumberOfRequests))
                .orElse(null);

        if (elementToRemove != null) {
            Long keyToRemove = this.getKeyByValue(cache, elementToRemove);
            cache.remove(keyToRemove);
        }
    }

    private Long getKeyByValue(Map<Long, CacheElement> cache, CacheElement value) {
        for (Map.Entry<Long, CacheElement> entry : cache.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Data
    private static class CacheElement {

        private Integer numberOfRequests;
        private Object value;

        public CacheElement(Integer numberOfRequests, Object value) {
            this.numberOfRequests = numberOfRequests;
            this.value = value;
        }
    }

}
