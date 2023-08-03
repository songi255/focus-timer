package com.focustimer.focustimer.config;

import com.focustimer.focustimer.config.autoscan.Component;

import java.io.*;
import java.util.Properties;

@Component
public class DataManager {
    private final Properties properties = new Properties();

    public DataManager() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(is);
        } catch (IOException e) {
            System.out.println("config.properties not found.");
            throw new RuntimeException(e);
        }
    }

    public String getData(String key){
        return properties.getProperty(key);
    }

    public void setData(String key, String value){
        properties.setProperty(key, value);
        saveToFile();
    }

    private void saveToFile(){
        try (OutputStream os = new FileOutputStream(getClass().getClassLoader().getResource("config.properties").getFile())) {
            properties.store(os, "saved properties");
        } catch (FileNotFoundException e) {
            System.out.println("config.properties file not found.");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateKey(int templateNum, String key){
        return "template-" + templateNum + "." + key;
    }
}
