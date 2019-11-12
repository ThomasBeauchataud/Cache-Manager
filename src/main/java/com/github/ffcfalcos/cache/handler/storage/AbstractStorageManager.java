package com.github.ffcfalcos.cache.handler.storage;

import java.io.Serializable;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * AbstractStorageManager is an abstract class to manage meta data
 */
public abstract class AbstractStorageManager implements StorageHandlerInterface {

    /**
     * Store a new Object with his key in cache
     * @param key String
     * @param object Serializable
     */
    @Override
    public void add(String key, Serializable object) {
        add(key, object, "empty-meta");
    }

    /**
     * Store a new Object with his key and his meta in cache
     * @param key String
     * @param object Serializable
     * @param meta Serializable
     */
    @Override
    public void add(String key, Serializable object, Serializable meta) {
        addKey("meta-"+key, meta);
        addKey(key, object);
    }

    /**
     * Delete a stored Object from the cache identified by his key
     * Make sur that the project has permission to delete a file !
     * @param key String
     */
    @Override
    public void remove(String key) {
        removeKey(key);
        removeKey("meta-"+key);
    }

    /**
     * Return meta data of a key
     * @param key String
     * @return Serializable
     */
    @Override
    public Serializable getMeta(String key) {
        return get("meta-"+key);
    }

    /**
     * Store a key with his simple content
     * @param key String
     * @param content Serializable
     */
    protected abstract void addKey(String key, Serializable content);

    /**
     * Remove a key from cache
     * @param key String
     */
    protected abstract void removeKey(String key);

}
