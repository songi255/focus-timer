package com.focustimer.focustimer.config.store;

import com.focustimer.focustimer.config.autoscan.Bean;
import com.focustimer.focustimer.config.autoscan.Component;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class DataInjector {
    private final Injector injector;
    private final DataManager dataManager;
    private final TemplateModel templateModel;

    @Inject
    public DataInjector(Injector injector, DataManager dataManager, TemplateModel templateModel) {
        this.injector = injector;
        this.dataManager = dataManager;
        this.templateModel = templateModel;

        this.templateModel.addTemplateNumListener(this::injectAll);
    }

    public void injectAll(){
        Set<Key<?>> beanKeys = injector.getBindings().keySet();

        for(var key : beanKeys){
            Object target = injector.getInstance(key);
            Class<?> targetClass = target.getClass().getSuperclass();

            if (targetClass.isAnnotationPresent(Bean.class)) {
                Method[] methods = targetClass.getMethods();
                Method beforeCall = null;
                Method afterCall = null;

                for(var method : methods){
                    if (method.isAnnotationPresent(BeforeDataInject.class)) beforeCall = method;
                    if (method.isAnnotationPresent(AfterDataInject.class)) afterCall = method;
                }

                try {
                    if (beforeCall != null) beforeCall.invoke(target);
                    inject(target);
                    if (afterCall != null) afterCall.invoke(target);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("method invocation error", e);
                }
            }
        }
    }

    public void inject(Object targetObj){
        Class<?> clazz = targetObj.getClass().getSuperclass();
        Field[] fields = clazz.getDeclaredFields();

        for(Field field : fields){
            Save save = field.getAnnotation(Save.class);
            SaveWithTemplate saveWithTemplate = field.getAnnotation(SaveWithTemplate.class);
            if (save == null && saveWithTemplate == null) continue;

            String key = clazz.getSimpleName() + "." +  field.getName();
            if (saveWithTemplate != null) key = DataManager.generateKey(templateModel.getTemplateNum(), key);

            String defaultValue = save == null ? saveWithTemplate.value() : save.value();

            String fieldName = field.getName();
            String setterName = "set" +
                    Character.toUpperCase(fieldName.charAt(0)) +
                    fieldName.substring(1);

            try {
                Class<?> fieldType = getWrapperClass(field.getType());
                String fetchedValue = Optional.<String>of(dataManager.getData(key)).orElse(defaultValue);

                Method setter = clazz.getMethod(setterName, field.getType());
                setter.invoke(targetObj, fieldType.getConstructor(String.class).newInstance(fetchedValue));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("setter not exists on " + clazz.getName(), e);
            } catch (InstantiationException e) {
                throw new RuntimeException("field type is not supported", e);
            }
        }
    }

    private Class<?> getWrapperClass(Class<?> clazz) {
        if (clazz.isPrimitive()){
            if (clazz.equals(boolean.class)) return Boolean.class;
            if (clazz.equals(byte.class)) return Byte.class;
            if (clazz.equals(short.class)) return Short.class;
            if (clazz.equals(char.class)) return Character.class;
            if (clazz.equals(int.class)) return Integer.class;
            if (clazz.equals(long.class)) return Long.class;
            if (clazz.equals(float.class)) return Float.class;
            if (clazz.equals(double.class)) return Double.class;
        }
        return clazz;
    }
}
