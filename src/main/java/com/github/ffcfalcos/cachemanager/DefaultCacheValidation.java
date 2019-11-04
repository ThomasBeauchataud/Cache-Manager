package com.github.ffcfalcos.cachemanager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 1.0.5
 * This class permit to validate a content stored in cache with his meta data
 */
class DefaultCacheValidation implements CacheValidationInterface {

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
            JSONObject jsonObject = (JSONObject) new JSONParser().parse((String)meta);
            return (long) jsonObject.get("expire") >= new Date().getTime();
        } catch (Exception e) {
            return true;
        }
    }

}
