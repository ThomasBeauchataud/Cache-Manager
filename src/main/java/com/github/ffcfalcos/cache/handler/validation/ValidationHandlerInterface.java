package com.github.ffcfalcos.cache.handler.validation;

import java.io.Serializable;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class permit to validate a content stored in cache with his meta data
 */
public interface ValidationHandlerInterface {

    /**
     * Validate or not the a content stored in cache with his meta data
     * @param meta Serializable
     * @return boolean
     */
    boolean validate(Serializable meta);

}
