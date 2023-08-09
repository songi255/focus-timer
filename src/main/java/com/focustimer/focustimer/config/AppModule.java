package com.focustimer.focustimer.config;

import com.focustimer.focustimer.config.autoscan.*;
import com.focustimer.focustimer.config.store.PersistenceProvider;
import com.focustimer.focustimer.config.store.Save;
import com.focustimer.focustimer.config.store.SaveWithTemplate;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ComponentScanModule("com.focustimer.focustimer", Bean.class, ServiceBean.class, Component.class));

        PersistenceProvider persistenceProvider = new PersistenceProvider(false);
        PersistenceProvider persistenceProviderWithTemplate = new PersistenceProvider(true);
        requestInjection(persistenceProvider);
        requestInjection(persistenceProviderWithTemplate);
        bindInterceptor(Matchers.annotatedWith(Bean.class), getSetterMatchers(Save.class), persistenceProvider);
        bindInterceptor(Matchers.annotatedWith(Bean.class), getSetterMatchers(SaveWithTemplate.class), persistenceProviderWithTemplate);
    }

    @SafeVarargs
    private Matcher<Method> getSetterMatchers(final Class<? extends Annotation> ...annotationClasses) {
        return new Matcher<Method>() {
            /**
             * Validates
             *  - method is setter.
             *  - field with setter exists.
             *  - field annotated with @Save or @SaveWithTemplate.
             *  - setter parameter count is 1.
             */
            @Override
            public boolean matches(Method method) {
                String methodName = method.getName();
                if (!methodName.startsWith("set")) return false;
                if (method.getParameterCount() != 1) return false;

                // get target field name from method name
                String targetFieldName = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);

                try {
                    Field targetField = method.getDeclaringClass().getDeclaredField(targetFieldName);
                    for (Class<? extends Annotation> annotationClass : annotationClasses){
                        if (targetField.isAnnotationPresent(annotationClass)) return true;
                    }
                } catch (NoSuchFieldException ignored) {}

                return false;
            }
        };
    }
}
