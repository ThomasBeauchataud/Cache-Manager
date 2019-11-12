package com.github.ffcfalcos.cache.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This annotation permit to store the result of method in cache and to invoke the cache in future call which its valid
 * Note that a class using this annotation must implements CacheablesInterface
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cacheables {

    /**
     * The name of the key to store
     * @return String
     */
    String key();

    /**
     * The name of a specific StorageHandler
     * @return String
     */
    String storageHandler() default "default";

    /**
     * The name of a specific ValidationHandler
     * @return String
     */
    String validationHandler() default "default";

    /**
     * Meta data
     * @return String
     */
    String meta() default "empty-meta";

}
