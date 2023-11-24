package com.focustimer.desktoptimer.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum DIContext {
    INSTANCE;

    public static enum Scope {
        TEMPORAL, SINGLETON;
    }

    private final Map<Class<?>, Object> context = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> clazz, Scope scope) {
        if (context.containsKey(clazz)){
            return (T) context.get(clazz);
        }

        if (scope.equals(Scope.SINGLETON)) {
            createSingleton(clazz);
            return (T) context.get(clazz);
        }

        return (T) createObject(clazz);
    }

    private <T> void createSingleton(Class<T> clazz) {
        synchronized (context) {
            if (!context.containsKey(clazz)) {
                T instance = createObject(clazz);
                context.put(clazz, instance);
            }
        }
    }

    private <T> T createObject(Class<T> clazz) {
        var constructors = clazz.getConstructors();
        var targetConstructor = Arrays.stream(constructors)
                .filter(constructor -> constructor.isAnnotationPresent(Inject.class))
                .findFirst()
                .orElseGet(() -> {
                    try {
                        return clazz.getConstructor(); // returns default constructor
                    } catch (NoSuchMethodException e) {
                        throw new IllegalArgumentException("Cannot find proper constructor.");
                    }
                });

        return invokeConstructor(targetConstructor);
    }

    @SuppressWarnings("unchecked")
    private <T> T invokeConstructor(Constructor<?> constructor) {
        Object[] parameters = getConstructorParameters(constructor);
        try {
            return (T) constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to call constructor.", e);
        }
    }

    private Object[] getConstructorParameters(Constructor<?> constructor) {
        return Arrays.stream(constructor.getParameterTypes())
                .map(clazz -> this.getInstance(clazz, Scope.SINGLETON))
                .toArray();
    }

    public void clear() {
        context.clear();
    }
}