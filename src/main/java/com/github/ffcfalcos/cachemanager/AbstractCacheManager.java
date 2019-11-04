package com.github.ffcfalcos.cachemanager;

import java.io.*;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 1.0.0
 * This abstract class allow CacheSystem to use serialize and unSerialize methods
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractCacheManager implements CacheSystemInterface {

    /**
     * Serialize an object to String
     * @param object Serializable
     * @return String
     */
    protected String serialize(Serializable object) {
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
    protected Serializable unSerialize(String object) {
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
