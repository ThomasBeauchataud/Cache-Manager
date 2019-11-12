package com.github.ffcfalcos.cache.interceptor;

import com.github.ffcfalcos.cache.CacheManagerInterface;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * Needed interface if a class wants to use @Cacheables annotation on a method
 */
public interface CacheablesInterface {

    /**
     * Return the cache manager
     * @return CacheManagerInterface
     */
    CacheManagerInterface getCacheManager();

}
