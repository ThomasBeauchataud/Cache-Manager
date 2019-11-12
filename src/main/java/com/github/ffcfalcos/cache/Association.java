package com.github.ffcfalcos.cache;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * Simple entity to stored how and where each key is stored
 */
class Association {

    private String storageHandlerName;
    private String key;
    private String validationHandlerName;

    /**
     * Association Constructor
     * @param key String the cache content key
     */
    Association(String key) {
        this.key = key;
    }

    /**
     * Association Constructor
     * @param storageHandlerName String the cache system named which store the key
     * @param key String the cache content key
     * @param validationHandlerName String the cache validation named which validate the content
     */
    Association(String storageHandlerName, String key, String validationHandlerName) {
        this.storageHandlerName = storageHandlerName;
        this.key = key;
        this.validationHandlerName = validationHandlerName;
    }

    String getStorageHandlerName() {
        return storageHandlerName;
    }

    void setStorageHandlerName(String storageHandlerName) {
        this.storageHandlerName = storageHandlerName;
    }

    String getKey() {
        return key;
    }

    String getValidationHandlerName() {
        return validationHandlerName;
    }

    void setValidationHandlerName(String validationHandlerName) {
        this.validationHandlerName = validationHandlerName;
    }

}
