package ru.clevertec.cache.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.cache.annotation.CustomCacheable;
import ru.clevertec.cache.cache.Cache;
import ru.clevertec.cache.config.CacheConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;


@Aspect
public class CacheAspect {

    private boolean findAllWasInvoked = false;

    private final Cache cache;

    public CacheAspect() {
        this.cache = CacheConfig.createCache();
    }

    @Pointcut("execution(* ru.clevertec.cache.repository.*.findById(..))")
    public void findByIdRepositoryMethod() {
    }

    @Pointcut("execution(* ru.clevertec.cache.repository.*.findAll(..))")
    public void findByAllRepositoryMethod() {
    }

    @Pointcut("execution(* ru.clevertec.cache.repository.*.save(..))")
    public void saveRepositoryMethod() {
    }

    @Pointcut("execution(* ru.clevertec.cache.repository.*.update(..))")
    public void updateRepositoryMethod() {
    }

    @Pointcut("execution(* ru.clevertec.cache.repository.*.delete(..))")
    public void deleteRepositoryMethod() {
    }

    @Around(value = "saveRepositoryMethod()")
    public Object cachingSave(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (checkAnnotation(proceedingJoinPoint)) {
            Object returnObject = proceedingJoinPoint.proceed();
            try {
                cache.put(findObjectId(returnObject), returnObject);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            return returnObject;
        }

        return proceedingJoinPoint.proceed();
    }

    @Around(value = "findByIdRepositoryMethod()")
    public Object cachingFindById(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (checkAnnotation(proceedingJoinPoint)) {
            Long id = (Long) proceedingJoinPoint.getArgs()[0];
            Object cachedObject = cache.get(id);

            if (cachedObject != null) {
                System.out.println("cache was called");
                return Optional.of(cachedObject);
            } else {
                Optional<?> returnObject = (Optional<?>) proceedingJoinPoint.proceed();
                returnObject.ifPresent(o -> cache.put(id, o));
                return returnObject;
            }
        }

        return proceedingJoinPoint.proceed();
    }

    @Around("findByAllRepositoryMethod()")
    public Object cachingFindByAll(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (checkAnnotation(proceedingJoinPoint)) {
            if (findAllWasInvoked) {
                return cache.getAll();
            } else {
                List<?> returnObject = (List<?>) proceedingJoinPoint.proceed();
                returnObject.forEach(object -> {
                    try {
                        cache.put(findObjectId(object), object);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
                findAllWasInvoked = true;
                return returnObject;
            }
        }

        return proceedingJoinPoint.proceed();
    }

    @Around("updateRepositoryMethod()")
    public Object cachingUpdate(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (checkAnnotation(proceedingJoinPoint)) {
            Object object = proceedingJoinPoint.getArgs()[0];

            Boolean returnValue = (Boolean) proceedingJoinPoint.proceed();
            if (returnValue) {
                cache.put(findObjectId(object), object);
            }
        }

        return proceedingJoinPoint.proceed();
    }

    @Around("deleteRepositoryMethod()")
    public Object cachingDelete(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (checkAnnotation(proceedingJoinPoint)) {
            Object object = proceedingJoinPoint.getArgs()[0];

            Boolean returnValue = (Boolean) proceedingJoinPoint.proceed();
            if (returnValue) {
                cache.delete(findObjectId(object));
            }
        }

        return proceedingJoinPoint.proceed();
    }

    private static boolean checkAnnotation(ProceedingJoinPoint proceedingJoinPoint) {
        String methodName = proceedingJoinPoint.getSignature().getName();
        for (Method method : proceedingJoinPoint.getTarget().getClass().getDeclaredMethods()) {
            if (method.getName().equals(methodName) && method.isAnnotationPresent(CustomCacheable.class)) {
                return true;
            }
        }
        return false;
    }

    private Long findObjectId(Object object) throws IllegalAccessException {
        Long id = null;

        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getName().equals("id")) {
                field.setAccessible(true);
                id = (Long) field.get(object);
                field.setAccessible(false);
            }
        }
        return id;
    }

}
