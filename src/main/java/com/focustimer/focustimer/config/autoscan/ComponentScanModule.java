/**
 * Guice Component Scan Configuration
 *
 */

package com.focustimer.focustimer.config.autoscan;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ComponentScanModule extends AbstractModule {
    private final String packageName;
    private final Set<Class<? extends Annotation>> bindingAnnotations;

    @SafeVarargs
    public ComponentScanModule(String packageName, final Class<? extends Annotation>... bindingAnnotations) {
        this.packageName = packageName;
        this.bindingAnnotations = new HashSet<>(Arrays.asList(bindingAnnotations));
    }

    @Override
    public void configure() {
        Reflections packageReflections = new Reflections(packageName);
        for(Class<? extends Annotation> annotationClass : bindingAnnotations){
            Set<Class<?>> classes =  packageReflections.getTypesAnnotatedWith(annotationClass);
            for(Class<?> clazz : classes){
                log.info("component scanned : " + clazz.getSimpleName());
                bind(clazz).in(Singleton.class);
            }
        }
    }
}