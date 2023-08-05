package com.focustimer.focustimer.config.store;

import com.focustimer.focustimer.config.autoscan.Component;
import com.focustimer.focustimer.model.template.TemplateModel;
import com.google.inject.Inject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Component
public class DataInjector {
    private final DataManager dataManager;
    private final TemplateModel templateModel;

    @Inject
    public DataInjector(DataManager dataManager, TemplateModel templateModel) {
        this.dataManager = dataManager;
        this.templateModel = templateModel;
    }

    public void inject(Object object){
        Class<?> clazz = object.getClass().getSuperclass();
        Field[] fields = clazz.getDeclaredFields();

        for(Field field : fields){
            boolean saveWithTemplate = false;
            if (field.isAnnotationPresent(SaveWithTemplate.class)){
                saveWithTemplate = true;
            } else if (!field.isAnnotationPresent(Save.class)) {
                continue;
            }
            String key = clazz.getSimpleName() + "." +  field.getName();
            if (saveWithTemplate) key = DataManager.generateKey(templateModel.getTemplateNum(), key);

            field.setAccessible(true);
            try {
                Class<?> fieldType = getWrapperClass(field.getType());
                field.set(object, fieldType.getConstructor(String.class).newInstance(dataManager.getData(key)));
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(false);
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
