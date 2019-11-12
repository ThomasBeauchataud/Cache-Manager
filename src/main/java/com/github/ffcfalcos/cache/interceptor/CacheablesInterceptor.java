package com.github.ffcfalcos.cache.interceptor;

import com.github.ffcfalcos.cache.CacheManagerInterface;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.io.Serializable;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * Aspect class which handle Cacheables Annotation Rules
 * Please watch @Cacheables documentation
 */
@Aspect
public class CacheablesInterceptor {

    @Pointcut("@annotation(cacheables)")
    public void cacheablesPointcut(Cacheables cacheables) { }

    @Around(value = "cacheablesPointcut(cacheables)", argNames = "proceedingJoinPoint, cacheables")
    public Object cacheablesPointcutHandler(ProceedingJoinPoint proceedingJoinPoint, Cacheables cacheables) throws Throwable {
        CacheManagerInterface cacheManager;
        try {
            cacheManager = ((CacheablesInterface) proceedingJoinPoint.getTarget()).getCacheManager();
        } catch (Exception e) {
            Exception exception = new Exception("You cant use the @Cacheables annotation on class which doesn't implements CacheablesInterface");
            exception.setStackTrace(e.getStackTrace());
            throw exception;
        }
        if(cacheManager.has(cacheables.key())) {
            return cacheManager.get(cacheables.key());
        } else {
            Object result = proceedingJoinPoint.proceed();
            try {
                cacheManager.add(cacheables.key(), (Serializable) result, cacheables.meta(), cacheables.storageHandler(), cacheables.validationHandler());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

}
