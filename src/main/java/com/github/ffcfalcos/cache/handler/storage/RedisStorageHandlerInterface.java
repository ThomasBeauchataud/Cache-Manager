package com.github.ffcfalcos.cache.handler.storage;

import redis.clients.jedis.Jedis;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class permit to manage a StorageHandler with simple actions and to store it in a Redis server
 * All rules are executed in the CacheManager
 * If you are using a ValidationHandler, you have to validate cache yourself or with the CacheManager
 */
@SuppressWarnings("unused")
public interface RedisStorageHandlerInterface extends StorageHandlerInterface {

    /**
     * Return the Redis Client
     * @return Jedis
     */
    Jedis getRedisClient();

}
