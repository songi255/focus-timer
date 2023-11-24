package com.focustimer.desktoptimer.common.store;

import com.focustimer.desktoptimer.common.DIContext;
import com.focustimer.desktoptimer.common.DIContext.Scope;
import javafx.beans.property.Property;

public class PersistingProperty {
    private static final KeyValueStorage storage =
            DIContext.INSTANCE.getInstance(KeyValueStorage.class, Scope.SINGLETON);

    @SuppressWarnings("unchecked")
    public static <T extends Property<?>> T create(Class<T> clazz, String key, String defaultValue) {
        try {
            String propertyClassName = clazz.getSimpleName();
            String propertyImplementClassName = "javafx.beans.property.Simple" + propertyClassName;
            String propertyWrappingTypeName =
                    "java.lang." + propertyClassName.substring(0, propertyClassName.indexOf("Property"));

            Class<?> wrappingType = Class.forName(propertyWrappingTypeName);
            Class<?> targetClass = Class.forName(propertyImplementClassName);

            String fetched = storage.getData(key);
            if (fetched == null) {
                fetched = defaultValue;
                storage.setData(key, fetched);
            }
            Object initialValue = wrappingType.equals(String.class) ?
                    fetched :
                    wrappingType.getMethod("valueOf", String.class).invoke(null, fetched);

            T target = (T) targetClass.getConstructor().newInstance();
            targetClass.getMethod("setValue", Object.class).invoke(target, initialValue);

            target.addListener((obs, oldValue, newValue) -> {
                storage.setData(key, String.valueOf(newValue));
            });

            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
