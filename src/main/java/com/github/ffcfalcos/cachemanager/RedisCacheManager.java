package com.github.ffcfalcos.cachemanager;

import redis.clients.jedis.Jedis;

import java.io.Serializable;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class permit to manage a cache system with simple actions and to store it in a Redis server
 * All rules are executed in the CacheManager and with the CacheValidationSystem if used
 */
class RedisCacheManager extends AbstractCacheManager {

    /**
     * Redis Client
     */
    private Jedis jedis = new Jedis();

    /**
     * Return a stored Object from cache identified by his key
     * @param key String
     * @return Serializable
     */
    @Override
    public Serializable get(String key) {
        return this.unSerialize(jedis.get(key));
    }

    /**
     * Store a new Object with his key in cache
     * @param key String
     * @param object Serializable
     */
    @Override
    public void add(String key, Serializable object) {
        jedis.lpush(key, this.serialize(object));
    }

    /**
     * Delete a stored Object from the cache identified by his key
     * @param key String
     */
    @Override
    public void remove(String key) {
        jedis.del(key);
    }

}
