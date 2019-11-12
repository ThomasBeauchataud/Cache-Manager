package com.github.ffcfalcos.cache.interceptor;

import com.github.ffcfalcos.cache.handler.storage.FileStorageHandlerInterface;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * Aspect class which throw custom exception if some of them arise
 */
@Aspect
public class ExceptionInterceptor {

    @Pointcut("execution(public void com.github.ffcfalcos.cache.handler.storage.RedisStorageHandler.remove(String))")
    public void redisStorageHandlerRemove() { }

    @Pointcut("execution(public java.io.Serializable com.github.ffcfalcos.cache.handler.storage.RedisStorageHandler.get(String))")
    public void redisStorageHandlerGet() { }

    @Pointcut("execution(public void com.github.ffcfalcos.cache.handler.storage.RedisStorageHandler.add(String, java.io.Serializable))")
    public void redisStorageHandlerAdd() { }

    @Pointcut("execution(public boolean com.github.ffcfalcos.cache.handler.storage.RedisStorageHandler.has(String))")
    public void redisStorageHandlerHas() { }

    @Pointcut("execution(public java.util.List<String> com.github.ffcfalcos.cache.handler.storage.RedisStorageHandler.keys())")
    public void redisStorageHandlerKeys() { }

    @Around("redisStorageHandlerAdd() || redisStorageHandlerGet() || redisStorageHandlerRemove() || redisStorageHandlerHas() || redisStorageHandlerKeys()")
    public Object catchRedisStorageHandlerException(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception e) {
            Exception exception = new Exception("An error arose while trying to manage cache with RedisStorageHandler");
            exception.setStackTrace(e.getStackTrace());
            throw exception;
        }
    }

    @Pointcut("execution(public void com.github.ffcfalcos.cache.handler.storage.FileStorageHandler.remove(String))")
    public void fileStorageHandlerRemove() { }

    @Pointcut("execution(public java.io.Serializable com.github.ffcfalcos.cache.handler.storage.FileStorageHandler.get(String))")
    public void fileStorageHandlerGet() { }

    @Pointcut("execution(public void com.github.ffcfalcos.cache.handler.storage.FileStorageHandler.add(String, java.io.Serializable))")
    public void fileStorageHandlerAdd() { }

    @Pointcut("execution(public boolean com.github.ffcfalcos.cache.handler.storage.FileStorageHandler.has(String))")
    public void fileStorageHandlerHas() { }

    @Pointcut("execution(public java.util.List<String> com.github.ffcfalcos.cache.handler.storage.FileStorageHandler.keys())")
    public void fileStorageHandlerKeys() { }

    @Around("fileStorageHandlerAdd() || fileStorageHandlerGet() || fileStorageHandlerRemove() || fileStorageHandlerHas() || fileStorageHandlerKeys()")
    public Object catchFileStorageHandlerException(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        FileStorageHandlerInterface fileStorageHandler = (FileStorageHandlerInterface) proceedingJoinPoint.getTarget();
        try {
            if(fileStorageHandler.getDirectory() == null) {
                throw new Exception("Impossible to reach cache files cause the directory path is null");
            }
            return proceedingJoinPoint.proceed();
        } catch (Exception e) {
            Exception exception = new Exception("An error arose while trying to manage cache with FileStorageHandler");
            exception.setStackTrace(e.getStackTrace());
            throw exception;
        }
    }

}
