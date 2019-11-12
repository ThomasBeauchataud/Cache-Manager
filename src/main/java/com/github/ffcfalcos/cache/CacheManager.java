package com.github.ffcfalcos.cache;

import com.github.ffcfalcos.cache.handler.storage.FileStorageHandler;
import com.github.ffcfalcos.cache.handler.storage.RedisStorageHandler;
import com.github.ffcfalcos.cache.handler.storage.StorageHandlerInterface;
import com.github.ffcfalcos.cache.handler.validation.DefaultValidationHandler;
import com.github.ffcfalcos.cache.handler.validation.ValidationHandlerInterface;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class if the manager of the component
 * It permit to manage cache with different storage system and different validation system
 * If you are using default handlers, please watch their description if they need some parameters to be initialized
 */
@Default
@ApplicationScoped
public final class CacheManager implements CacheManagerInterface {

    private List<StorageHandlerInterface> storageHandlers = new ArrayList<>();
    private List<ValidationHandlerInterface> validationHandlers = new ArrayList<>();
    private List<Association> associations = new ArrayList<>();
    private StorageHandlerInterface defaultStorageHandler;
    private ValidationHandlerInterface defaultValidationHandler;

    /**
     * CacheManager Constructor
     */
    public CacheManager() {
        defaultStorageHandler = new FileStorageHandler();
        addStorageHandler(defaultStorageHandler);
        addStorageHandler(new RedisStorageHandler());
        defaultValidationHandler = new DefaultValidationHandler();
        addValidationHandler(defaultValidationHandler);
    }

    /**
     * Add a ValidationHandler in the CacheManager to use it later
     * @param cacheValidation ValidationHandlerInterface
     */
    @Override
    public void addValidationHandler(ValidationHandlerInterface cacheValidation) {
        for(ValidationHandlerInterface cacheValidationTest : this.validationHandlers) {
            if(cacheValidationTest.getClass() == cacheValidation.getClass()) {
                return;
            }
        }
        this.validationHandlers.add(cacheValidation);
    }

    /**
     * Add a list of ValidationHandler in the CacheManager to use them later
     * @param validationHandlers ValidationHandlerInterface[]
     */
    @Override
    public void addValidationHandlers(List<ValidationHandlerInterface> validationHandlers) {
        for(ValidationHandlerInterface cacheValidation : validationHandlers) {
            this.addValidationHandler(cacheValidation);
        }
    }

    /**
     * Add a StorageHandler in the CacheManager to use it later
     * @param cacheSystem StorageHandlerInterface
     */
    @Override
    public void addStorageHandler(StorageHandlerInterface cacheSystem) {
        for(StorageHandlerInterface cacheSystemTest : this.storageHandlers) {
            if(cacheSystemTest.getClass() == cacheSystem.getClass()) {
                return;
            }
        }
        this.storageHandlers.add(cacheSystem);
        for(String key : cacheSystem.keys()) {
            associations.add(new Association(cacheSystem.getClass().getSimpleName(), key, defaultValidationHandler.getClass().getName()));
        }
    }

    /**
     * Add a list of StorageHandler in the CacheManager to use them later
     * @param storageHandlers StorageHandlerInterface[]
     */
    @Override
    public void addStorageHandlers(List<StorageHandlerInterface> storageHandlers) {
        for(StorageHandlerInterface cacheSystem : storageHandlers) {
            this.addStorageHandler(cacheSystem);
        }
    }

    /**
     * Return a stored Object from cache identified by his key
     * @param key String
     * @return Serializable
     */
    @Override
    public Serializable get(String key) {
        if(this.has(key)) {
            Association association = this.getAssociationByKey(key);
            assert association != null;
            StorageHandlerInterface cacheSystem = this.getStorageHandler(association.getStorageHandlerName());
            return cacheSystem.get(key);
        }
        return null;
    }

    /**
     * Return true if a content stored in cache identified by his key exists
     * @param key String
     * @return boolean
     */
    @Override
    public boolean has(String key) {
        Association association = this.getAssociationByKey(key);
        if(association != null) {
            StorageHandlerInterface cacheSystem = this.getStorageHandler(association.getStorageHandlerName());
            Serializable meta = cacheSystem.getMeta(key);
            ValidationHandlerInterface cacheValidation = this.getValidationHandler(association.getValidationHandlerName());
            return cacheValidation.validate(meta);
        }
        return false;
    }

    /**
     * Add object in the default StorageHandler and with default ValidationHandler
     * @param key String
     * @param object Serializable
     */
    @Override
    public void add(String key, Serializable object) {
        add(key, object, "empty-meta");
    }

    /**
     * Add object in the default StorageHandler and with default ValidationHandler
     * @param key String
     * @param object Serializable
     * @param meta Serializable, please watch default validation system if you are using it
     */
    @Override
    public void add(String key, Serializable object, Serializable meta) {
        add(key, object, meta, defaultStorageHandler.getClass().getName(), defaultValidationHandler.getClass().getName());
    }

    /**
     * Add object in the cache with a specific StorageHandler and a specific ValidationHandler
     * If StorageHandler or ValidationHandler doesn't exists default handlers will be used
     * @param key String
     * @param object Serializable
     * @param meta Serializable
     * @param storageHandlerName the name of the StorageHandler class
     * @param validationHandlerName String the name of the ValidationHandler class
     */
    @Override
    public void add(String key, Serializable object, Serializable meta, String storageHandlerName, String validationHandlerName) {
        Association association = new Association(key);
        StorageHandlerInterface cacheSystem = this.getStorageHandler(storageHandlerName);
        ValidationHandlerInterface cacheValidation = this.getValidationHandler(validationHandlerName);
        association.setStorageHandlerName(cacheSystem.getClass().getName());
        association.setValidationHandlerName(cacheValidation.getClass().getName());
        if(this.getAssociationByKey(key) != null) {
            cacheSystem.remove(key);
        }
        cacheSystem.add(key, object, meta);
        associations.add(association);
    }

    /**
     * Delete a key from the cache
     * @param key String
     */
    @Override
    public void remove(String key) {
        Association association = this.getAssociationByKey(key);
        if(association != null) {
            StorageHandlerInterface cacheSystem = this.getStorageHandler(association.getStorageHandlerName());
            cacheSystem.remove(key);
        }
    }

    /**
     * Return all keys stored in cache
     * @return String[]
     */
    @Override
    public List<String> keys() {
        List<String> keys = new ArrayList<>();
        for(Association association : this.associations) {
            if(this.has(association.getKey())) {
                keys.add(association.getKey());
            }
        }
        return keys;
    }

    /**
     * Return a StorageHandlerInterface identified by his class name
     * @param storageHandlerName String
     * @return StorageHandlerInterface
     */
    @Override
    public StorageHandlerInterface getStorageHandler(String storageHandlerName) {
        for(StorageHandlerInterface cacheSystem : this.storageHandlers) {
            if(cacheSystem.getClass().getSimpleName().equals(storageHandlerName)) {
                return cacheSystem;
            }
        }
        return defaultStorageHandler;
    }

    /**
     * Change the default StorageHandler
     * @param storageHandlerName String
     */
    @Override
    public void setDefaultStorageHandler(String storageHandlerName) {
        defaultStorageHandler = getStorageHandler(storageHandlerName);
    }

    /**
     * Return a ValidationHandlerInterface identified by his class name
     * @param cacheValidationName String
     * @return ValidationHandlerInterface
     */
    @Override
    public ValidationHandlerInterface getValidationHandler(String cacheValidationName) {
        for(ValidationHandlerInterface cacheValidation : this.validationHandlers) {
            if(cacheValidation.getClass().getName().equals(cacheValidationName)) {
                return cacheValidation;
            }
        }
        return defaultValidationHandler;
    }

    /**
     * Change the default ValidationHandler
     * @param validationHandlerName String
     */
    @Override
    public void setDefaultValidationHandler(String validationHandlerName) {
        defaultValidationHandler = getValidationHandler(validationHandlerName);
    }

    /**
     * Return an Association identified by his key
     * @param key String
     * @return Association
     */
    private Association getAssociationByKey(String key) {
        for(Association association : associations) {
            if(association.getKey().equals(key)) {
                return association;
            }
        }
        return null;
    }

}
