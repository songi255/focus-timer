package com.focustimer.focustimer.config.store;

import com.focustimer.focustimer.config.autoscan.Component;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Properties;

@Slf4j
@Component
public class DataManager {
    private final Properties properties = new Properties();
    @Inject
    public DataManager() {
        try (InputStream is = getClass().getResourceAsStream("/com/focustimer/focustimer/config.properties")) {
            properties.load(is);
        } catch (IOException e) {
            log.error("config.properties not found.");
            throw new RuntimeException(e);
        }
    }

    public String getData(String key){
        return properties.getProperty(key);
    }

    public void setData(String key, String value){
        String oldValue = getData(key);
        if (value.equals(oldValue)) return;

        properties.setProperty(key, value);
        saveToFile();

        log.info("saved data with key : " + key + ", old value : " + oldValue + ", to new value : " + value);
    }

    private void saveToFile(){
        try (OutputStream os = new FileOutputStream(getClass().getResource("/com/focustimer/focustimer/config.properties").getFile())) {
            properties.store(os, "saved properties");
        } catch (IOException e) {
            log.error("config.properties file not found.");
            throw new RuntimeException(e);
        }
    }

    public static String generateKey(int templateNum, String... keys){
        StringBuilder sb = new StringBuilder("template-");
        sb.append(templateNum);
        for(String key : keys){
            sb.append('.').append(key);
        }
        return sb.toString();
    }
}
