package com.ts.core.caching;


public interface ICacheConfiguration {

    void clearAllCaches();
    void evictCacheByKey(String cacheName, Object key);
    void clearCacheByName(String cacheName);
    void addToCache(String cacheName, Object key, Object value);
    void updateCache(String cacheName, Object key, Object value);
    <T> T getFromCache(String cacheName, Object key, Class<T> type);
}