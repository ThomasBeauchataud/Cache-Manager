package com.github.ffcfalcos.cachemanager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 1.0.0
 * This class if the manager of the component
 * It permit to manage cache with different storage system and different validation system
 * If you are using default system, please watch their description if they need some parameters to be initialized
 */
@Default
@ApplicationScoped
class CacheManager implements CacheManagerInterface {

    @Inject
    private RedisCacheManager redisCacheManager;
    @Inject
    private FileCacheManager fileCacheManager;
    @Inject
    private DefaultCacheValidation defaultCacheValidation;

    private List<CacheSystemInterface> cacheSystems = new ArrayList<>();
    private List<CacheValidationInterface> cacheValidations = new ArrayList<>();
    private List<Association> associations = new ArrayList<>();

    /**
     * Initialize the CacheManager by adding default systems in the system list
     */
    @PostConstruct
    public void init() {
        cacheSystems.add(redisCacheManager);
        cacheSystems.add(fileCacheManager);
        cacheValidations.add(defaultCacheValidation);
    }

    /**
     * Add a CacheValidationInterface in the CacheManager to use it later
     * @param cacheValidation CacheValidationInterface
     */
    @Override
    public void addCacheValidation(CacheValidationInterface cacheValidation) {
        for(CacheValidationInterface cacheValidationTest : this.cacheValidations) {
            if(cacheValidationTest.getClass() == cacheValidation.getClass()) {
                return;
            }
        }
        this.cacheValidations.add(cacheValidation);
    }

    /**
     * Add a list of CacheValidationInterface in the CacheManager to use them later
     * @param cacheValidations CacheValidationInterface[]
     */
    @Override
    public void addCacheValidations(List<CacheValidationInterface> cacheValidations) {
        for(CacheValidationInterface cacheValidation : cacheValidations) {
            this.addCacheValidation(cacheValidation);
        }
    }

    /**
     * Add a CacheSystemInterface in the CacheManager to use it later
     * @param cacheSystem CacheSystemInterface
     */
    @Override
    public void addCacheSystem(CacheSystemInterface cacheSystem) {
        for(CacheSystemInterface cacheSystemTest : this.cacheSystems) {
            if(cacheSystemTest.getClass() == cacheSystem.getClass()) {
                return;
            }
        }
        this.cacheSystems.add(cacheSystem);
    }

    /**
     * Add a list of CacheSystemInterface in the CacheManager to use them later
     * @param cacheSystems CacheSystemInterface[]
     */
    @Override
    public void addCacheSystems(List<CacheSystemInterface> cacheSystems) {
        for(CacheSystemInterface cacheSystem : cacheSystems) {
            this.addCacheSystem(cacheSystem);
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
            CacheSystemInterface cacheSystem = this.getCacheSystem(association.getCacheSystem());
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
            CacheSystemInterface cacheSystem = this.getCacheSystem(association.getCacheSystem());
            Serializable meta = cacheSystem.get("meta-" + key);
            CacheValidationInterface cacheValidation = this.getCacheValidation(association.getValidationSystem());
            return cacheValidation.validate(meta);
        }
        return false;
    }

    /**
     * Add object in the default cache system (Redis) and with default validation system
     * @param key String
     * @param object Serializable
     * @param meta Serializable, please watch default validation system if you are using it
     */
    @Override
    public void add(String key, Serializable object, Serializable meta) {
        redisCacheManager.add(key, object);
        redisCacheManager.add("meta-" + key, meta);
        associations.add(new Association(redisCacheManager.getClass().getName(), key, defaultCacheValidation.getClass().getName()));
    }

    /**
     * Add object in the cache with a specific cache system and a specific validation system
     * If cache system and validation system doesn't exists default systems will be used
     * @param key String
     * @param object Serializable
     * @param meta Serializable
     * @param cacheSystemName String the name of the cache system class
     * @param validationSystemName String the name of the cache validation class
     */
    @Override
    public void add(String key, Serializable object, Serializable meta, String cacheSystemName, String validationSystemName) {
        Association association = new Association(key);
        CacheSystemInterface cacheSystem = this.getCacheSystem(cacheSystemName);
        CacheValidationInterface cacheValidation = this.getCacheValidation(validationSystemName);
        association.setCacheSystem(cacheSystem.getClass().getName());
        association.setValidationSystem(cacheValidation.getClass().getName());
        if(this.getAssociationByKey(key) != null) {
            cacheSystem.remove(key);
            cacheSystem.remove("meta-" + key);
        }
        cacheSystem.add(key, object);
        cacheSystem.add("meta-" + key, meta);
    }

    /**
     * Delete a key from the cache
     * @param key String
     */
    @Override
    public void remove(String key) {
        Association association = this.getAssociationByKey(key);
        if(association != null) {
            CacheSystemInterface cacheSystem = this.getCacheSystem(association.getCacheSystem());
            cacheSystem.remove(key);
            cacheSystem.remove("meta-" + key);
        }
    }

    /**
     * Return a CacheSystemInterface identified by his class name
     * @param cacheSystemName String
     * @return CacheSystemInterface
     */
    private CacheSystemInterface getCacheSystem(String cacheSystemName) {
        for(CacheSystemInterface cacheSystem : this.cacheSystems) {
            if(cacheSystem.getClass().getName().equals(cacheSystemName)) {
                return cacheSystem;
            }
        }
        return redisCacheManager;
    }

    /**
     * Return a CacheValidationInterface identified by his class name
     * @param cacheValidationName String
     * @return CacheValidationInterface
     */
    private CacheValidationInterface getCacheValidation(String cacheValidationName) {
        for(CacheValidationInterface cacheValidation : this.cacheValidations) {
            if(cacheValidation.getClass().getName().equals(cacheValidationName)) {
                return cacheValidation;
            }
        }
        return defaultCacheValidation;
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
