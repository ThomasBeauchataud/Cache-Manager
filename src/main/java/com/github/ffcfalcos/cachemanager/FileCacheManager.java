package com.github.ffcfalcos.cachemanager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.*;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 1.0.7
 * This class permit to manage a cache system with simple actions and to store it in files
 * The files path is created with env-entry parameters
 *      cache-path, the absolute path to cache files
 * All rules are executed in the CacheManager and with the CacheValidationSystem if used
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
class FileCacheManager implements CacheSystemInterface {

    /**
     * File Cache directory
     */
    private String directory;

    /**
     * FileCacheManager Constructor
     * Load the env-entry parameters to define cache file directory
     */
    FileCacheManager() {
        try {
            Context env = (Context)new InitialContext().lookup("java:comp/env");
            directory = (String) env.lookup("cache-path");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return a stored Object from cache identified by his key
     * @param key String
     * @return Serializable
     */
    @Override
    public Serializable get(String key) {
        try {
            FileInputStream fileInputStream = new FileInputStream(directory + key);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Serializable)objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Store a new Object with his key in cache
     * @param key String
     * @param object Serializable
     */
    @Override
    public void add(String key, Serializable object) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(directory + key);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a stored Object from the cache identified by his key
     * @param key String
     */
    @Override
    public void remove(String key) {
        File file = new File(directory + key);
        file.delete();
    }

}
