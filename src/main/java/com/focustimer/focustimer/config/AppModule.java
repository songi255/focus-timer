package com.focustimer.focustimer.config;

import com.focustimer.focustimer.config.autoscan.*;
import com.google.inject.AbstractModule;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ComponentScanModule("com.focustimer.focustimer", Bean.class, Service.class, Component.class));
    }
}
