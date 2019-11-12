package com.github.ffcfalcos.cache.handler.storage;

import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class permit to manage a StorageHandler with simple actions and to store it in a Redis server
 * All rules are executed in the CacheManager and with the ValidationHandler if used
 */
public class RedisStorageHandler extends AbstractStorageManager implements RedisStorageHandlerInterface {

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
     * Store a key with his simple content
     * @param key String
     * @param object Serializable
     */
    @Override
    protected void addKey(String key, Serializable object) {
        jedis.lpush(key, this.serialize(object));
    }

    /**
     * Remove a key from cache
     * @param key String
     */
    @Override
    public void removeKey(String key) {
        jedis.del(key);
    }

    @Override
    public boolean has(String key) {
        return jedis.exists(key);
    }

    @Override
    public List<String> keys() {
        return new ArrayList<>(jedis.keys(""));
    }

    /**
     * Return the Redis Client
     * @return Jedis
     */
    @Override
    public Jedis getRedisClient() {
        return jedis;
    }

    /**
     * Serialize an object to String
     * @param object Serializable
     * @return String
     */
    private String serialize(Serializable object) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(object);
            so.flush();
            return bo.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * UnSerialize a String to an object
     * @param object String
     * @return Serializable
     */
    private Serializable unSerialize(String object) {
        try {
            byte[] b = object.getBytes();
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            return (Serializable) si.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
