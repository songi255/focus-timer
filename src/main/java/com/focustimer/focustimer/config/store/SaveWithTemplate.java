package com.focustimer.focustimer.config.store;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SaveWithTemplate {
    /**
     * initialize with defaultValue when data not exists
     */
    String value() default "";
}
