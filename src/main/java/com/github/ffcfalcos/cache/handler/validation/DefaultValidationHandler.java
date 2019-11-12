package com.github.ffcfalcos.cache.handler.validation;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class permit to validate a content stored in cache with his meta data
 */
@SuppressWarnings("unchecked")
public class DefaultValidationHandler implements ValidationHandlerInterface {

    /**
     * Validate or not the a content stored in cache with his meta data
     * This validation system need a json string as meta data
     * The different fields are
     *      expire, the timestamp of the expiration of the content
     * @param meta Serializable
     * @return boolean
     */
    @Override
    public boolean validate(Serializable meta) {
        try {
            Map<String, Object> metaContent = (Map<String, Object>) meta;
            return (long) metaContent.get("expire") >= new Date().getTime();
        } catch (Exception e) {
            return true;
        }
    }

}
