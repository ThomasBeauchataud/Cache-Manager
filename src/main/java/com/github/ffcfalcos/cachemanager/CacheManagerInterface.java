package com.github.ffcfalcos.cachemanager;

import java.io.Serializable;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 1.0.5
 * This class if the manager of the component
 * It permit to manage cache with different storage system and different validation system
 * If you are using default system, please watch their description if they need some parameters to be initialized
 */
public interface CacheManagerInterface {

    /**
     * Add a CacheValidationInterface in the CacheManager to use it later
     * @param cacheValidation CacheValidationInterface
     */
    void addCacheValidation(CacheValidationInterface cacheValidation);

    /**
     * Add a list of CacheValidationInterface in the CacheManager to use them later
     * @param cacheValidations CacheValidationInterface[]
     */
    void addCacheValidations(List<CacheValidationInterface> cacheValidations);

    /**
     * Add a CacheSystemInterface in the CacheManager to use it later
     * @param cacheSystem CacheSystemInterface
     */
    void addCacheSystem(CacheSystemInterface cacheSystem);

    /**
     * Add a list of CacheSystemInterface in the CacheManager to use them later
     * @param cacheSystems CacheSystemInterface[]
     */
    void addCacheSystems(List<CacheSystemInterface> cacheSystems);

    /**
     * Return a stored Object from cache identified by his key
     * @param key String
     * @return Serializable
     */
    Serializable get(String key);

    /**
     * Return true if a content stored in cache identified by his key exists
     * @param key String
     * @return boolean
     */
    boolean has(String key);

    /**
     * Add object in the default cache system (Redis) and with default validation system
     * @param key String
     * @param object Serializable
     */
    void add(String key, Serializable object);

    /**
     * Add object in the default cache system (Redis) and with default validation system
     * @param key String
     * @param object Serializable
     * @param meta Serializable, please watch default validation system if you are using it
     */
    void add(String key, Serializable object, Serializable meta);

    /**
     * Add object in the cache with a specific cache system and a specific validation system
     * If cache system and validation system doesn't exists default systems will be used
     * @param key String
     * @param object Serializable
     * @param meta Serializable
     * @param cacheSystemName String the name of the cache system class
     * @param validationSystemName String the name of the cache validation class
     */
    void add(String key, Serializable object, Serializable meta, String cacheSystemName, String validationSystemName);

    /**
     * Delete a key from the cache
     * @param key String
     */
    void remove(String key);

}
