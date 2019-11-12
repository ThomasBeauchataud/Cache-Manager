package com.github.ffcfalcos.cache.handler.storage;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class permit to manage a StorageHandler with simple actions and to store it in files
 * The files path is created with env-entry parameters
 *      cache-path, the absolute path to cache files
 * All rules are executed in the CacheManager and with the ValidationHandler if used
 */
@SuppressWarnings({"ResultOfMethodCallIgnored","empty"})
public class FileStorageHandler extends AbstractStorageManager implements FileStorageHandlerInterface {

    /**
     * File Cache directory
     */
    private String directory = loadDirectoryPath();

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
     * Store a key with his simple content
     * @param key String
     * @param object Serializable
     */
    @Override
    protected void addKey(String key, Serializable object) {
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
     * Make sur that the project has permission to delete a file !
     * @param key String
     */
    @Override
    protected void removeKey(String key) {
        File file = new File(directory + key);
        file.delete();
    }

    /**
     * Return true if a StorageManager has a key stored
     * @param key String
     * @return boolean
     */
    @Override
    public boolean has(String key) {
        File folder = new File(directory);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isFile() && file.getName().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return all keys stored in the StorageHandler
     * @return String[]
     */
    @Override
    public List<String> keys() {
        List<String> keys = new ArrayList<>();
        File folder = new File(directory);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isFile()) {
                keys.add(file.getName());
            }
        }
        return keys;
    }

    /**
     * Change the path to the directory where to store cache files
     * @param directoryPath String
     */
    @Override
    public void setDirectory(String directoryPath) {
        directory = directoryPath;
    }

    /**
     * Return the path to the directory where to store cache files
     * @return String
     */
    @Override
    public String getDirectory() {
        return directory;
    }

    /**
     * Load the env-entry parameters to define cache file directory
     * @return String
     */
    private String loadDirectoryPath() {
        try {
            Context env = (Context)new InitialContext().lookup("java:comp/env");
            return (String) env.lookup("cache-path");
        } catch (NamingException e) {
            return null;
        }
    }

}
