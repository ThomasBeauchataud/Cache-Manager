package com.github.ffcfalcos.cachemanager;

import java.io.Serializable;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 1.0.1
 * This class permit to manage a cache system with simple actions
 * All rules are executed in the CacheManager and with the CacheValidationSystem if used
 */
public interface CacheSystemInterface {

    /**
     * Return a stored Object from cache identified by his key
     * @param key String
     * @return Serializable
     */
    Serializable get(String key);

    /**
     * Store a new Object with his key in cache
     * @param key String
     * @param object Serializable
     */
    void add(String key, Serializable object);

    /**
     * Delete a stored Object from the cache identified by his key
     * @param key String
     */
    void remove(String key);

}
