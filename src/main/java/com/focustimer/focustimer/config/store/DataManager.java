package com.focustimer.focustimer.config.store;

import com.focustimer.focustimer.config.autoscan.Component;
import com.focustimer.focustimer.model.template.TemplateModel;
import com.google.inject.Inject;
import javafx.print.PrintColor;

import java.io.*;
import java.util.Properties;

@Component
public class DataManager {
    private final Properties properties = new Properties();
    @Inject
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
        System.out.println("saved data with key : " + key + ", value : " + value);
    }

    private void saveToFile(){
        try (OutputStream os = new FileOutputStream(getClass().getClassLoader().getResource("config.properties").getFile())) {
            properties.store(os, "saved properties");
        } catch (IOException e) {
            System.out.println("config.properties file not found.");
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
