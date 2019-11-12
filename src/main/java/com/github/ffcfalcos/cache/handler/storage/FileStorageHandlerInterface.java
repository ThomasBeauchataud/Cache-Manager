package com.github.ffcfalcos.cache.handler.storage;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class permit to manage a StorageHandler with simple actions and to store it in files
 * The files path is created with env-entry parameters
 *      cache-path, the absolute path to cache files
 * All rules are executed in the CacheManager
 * If you are using a ValidationHandler, you have to validate cache yourself or with the CacheManager
 */
@SuppressWarnings("unused")
public interface FileStorageHandlerInterface extends StorageHandlerInterface {

    /**
     * Change the path to the directory where to store cache files
     * @param directoryPath String
     */
    void setDirectory(String directoryPath);

    /**
     * Return the path to the directory where to store cache files
     * @return String
     */
    String getDirectory();

}
