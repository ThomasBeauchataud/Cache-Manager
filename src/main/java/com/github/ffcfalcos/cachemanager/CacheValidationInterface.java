package com.github.ffcfalcos.cachemanager;

import java.io.Serializable;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 1.0.1
 * This class permit to validate a content stored in cache with his meta data
 */
public interface CacheValidationInterface {

    /**
     * Validate or not the a content stored in cache with his meta data
     * @param meta Serializable
     * @return boolean
     */
    boolean validate(Serializable meta);

}
