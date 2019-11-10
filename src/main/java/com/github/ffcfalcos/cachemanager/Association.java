package com.github.ffcfalcos.cachemanager;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * Simple entity to stored how and where each key is stored
 */
class Association {

    private String cacheSystem;
    private String key;
    private String validationSystem;

    /**
     * Association Constructor
     * @param key String the cache content key
     */
    Association(String key) {
        this.key = key;
    }

    /**
     * Association Constructor
     * @param cacheSystem String the cache system named which store the key
     * @param key String the cache content key
     * @param validationSystem String the cache validation named which validate the content
     */
    Association(String cacheSystem, String key, String validationSystem) {
        this.cacheSystem = cacheSystem;
        this.key = key;
        this.validationSystem = validationSystem;
    }

    String getCacheSystem() {
        return cacheSystem;
    }

    void setCacheSystem(String cacheSystem) {
        this.cacheSystem = cacheSystem;
    }

    String getKey() {
        return key;
    }

    String getValidationSystem() {
        return validationSystem;
    }

    void setValidationSystem(String validationSystem) {
        this.validationSystem = validationSystem;
    }

}
