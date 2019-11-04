package com.github.ffcfalcos.cachemanager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.*;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 1.0.1
 * This class permit to manage a cache system with simple actions and to store it in files
 * The files path is created with env-entry parameters
 *      cache-path, the absolute path to cache files
 * All rules are executed in the CacheManager and with the CacheValidationSystem if used
 */
@ApplicationScoped
@SuppressWarnings("ResultOfMethodCallIgnored")
class FileCacheManager extends AbstractCacheManager {

    /**
     * File Cache directory
     */
    private String directory;

    /**
     * Load the env-entry parameters to define cache file directory
     */
    @PostConstruct
    public void init() {
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
            File file = new File(directory + key);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            StringBuilder string = new StringBuilder();
            while ((st = br.readLine()) != null) {
                string.append(st);
            }
            return this.unSerialize(string.toString());
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(directory + key));
            writer.write(this.serialize(object));
            writer.close();
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
