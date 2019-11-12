package com.github.ffcfalcos.cache.handler.storage;

import java.io.Serializable;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class permit to manage a cache system with simple actions
 * All rules are executed in the CacheManager
 * If you are using a ValidationHandler, you have to validate cache yourself or with the CacheManager
 */
public interface StorageHandlerInterface {

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
     * Store a new Object with his key and his meta in cache
     * @param key String
     * @param object Serializable
     * @param meta Serializable
     */
    void add(String key, Serializable object, Serializable meta);

    /**
     * Delete a stored Object from the cache identified by his key
     * @param key String
     */
    void remove(String key);

    /**
     * Return true if a StorageManager has a key stored
     * @param key String
     * @return boolean
     */
    boolean has(String key);

    /**
     * Return meta data of a key
     * @param key String
     * @return Serializable
     */
    Serializable getMeta(String key);

    /**
     * Return all keys stored in the StorageHandler
     * @return String[]
     */
    List<String> keys();

}
