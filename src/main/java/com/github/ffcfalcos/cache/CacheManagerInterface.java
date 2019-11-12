package com.github.ffcfalcos.cache;

import com.github.ffcfalcos.cache.handler.storage.StorageHandlerInterface;
import com.github.ffcfalcos.cache.handler.validation.ValidationHandlerInterface;

import java.io.Serializable;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class if the manager of the component
 * It permit to manage cache with different storage system and different validation system
 * If you are using default system, please watch their description if they need some parameters to be initialized
 */
@SuppressWarnings("unused")
public interface CacheManagerInterface {

    /**
     * Add a ValidationHandler in the CacheManager to use it later
     * @param cacheValidation ValidationHandlerInterface
     */
    void addValidationHandler(ValidationHandlerInterface cacheValidation);

    /**
     * Add a list of ValidationHandler in the CacheManager to use them later
     * @param cacheValidations ValidationHandlerInterface[]
     */
    void addValidationHandlers(List<ValidationHandlerInterface> cacheValidations);

    /**
     * Return a ValidationHandler identified by his name
     * If it doesnt exists, it returns the default ValidationHandler
     * @param validationHandlerName String
     * @return ValidationHandlerInterface
     */
    ValidationHandlerInterface getValidationHandler(String validationHandlerName);

    /**
     * Change the default ValidationHandler
     * @param validationHandlerName String
     */
    void setDefaultValidationHandler(String validationHandlerName);

    /**
     * Add a StorageHandler in the CacheManager to use it later
     * @param cacheSystem StorageHandlerInterface
     */
    void addStorageHandler(StorageHandlerInterface cacheSystem);

    /**
     * Add a list of StorageHandler in the CacheManager to use them later
     * @param cacheSystems StorageHandlerInterface[]
     */
    void addStorageHandlers(List<StorageHandlerInterface> cacheSystems);

    /**
     * Return a StorageHandler identified by his name
     * If it doesnt exists, it returns the default StorageHandler
     * @param storageHandlerName String
     * @return StorageHandlerInterface
     */
    StorageHandlerInterface getStorageHandler(String storageHandlerName);

    /**
     * Change the default StorageHandler
     * @param storageHandlerName String
     */
    void setDefaultStorageHandler(String storageHandlerName);

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
     * Add object in the default handlers
     * @param key String
     * @param object Serializable
     */
    void add(String key, Serializable object);

    /**
     * Add object in the default handlers
     * @param key String
     * @param object Serializable
     * @param meta Serializable, please watch default validation system if you are using it
     */
    void add(String key, Serializable object, Serializable meta);

    /**
     * Add object in the cache with a specific StorageHandler and a specific ValidationHandler
     * If StorageHandler or ValidationHandler doesn't exists default handlers will be used
     * @param key String
     * @param object Serializable
     * @param meta Serializable
     * @param storageHandlerName the name of the StorageHandler class
     * @param validationHandlerName String the name of the ValidationHandler class
     */
    void add(String key, Serializable object, Serializable meta, String storageHandlerName, String validationHandlerName);

    /**
     * Delete a key from the cache
     * @param key String
     */
    void remove(String key);

    /**
     * Return all keys stored in cache
     * @return String[]
     */
    List<String> keys();

}
