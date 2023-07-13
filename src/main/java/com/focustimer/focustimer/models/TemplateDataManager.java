package com.focustimer.focustimer.models;

import java.io.*;
import java.nio.file.Path;
import java.util.Properties;

public class TemplateDataManager {
    // TODO : path binding
    private final Properties properties = new Properties();

    public TemplateDataManager() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(is);
        } catch (IOException e) {
            System.out.println("config.properties not found.");
            throw new RuntimeException(e);
        }
    }

    public void createNewTemplate(int templateNum){

    }

    public void deleteTemplate(int templateNum){

    }

    public void saveData(String key, String value){
        String realKey = getRealKey(key);
        this.properties.setProperty(realKey, value);
        saveToFile();
    }

    private void saveToFile(){
        try (OutputStream os = new FileOutputStream(getClass().getClassLoader().getResource("config.properties").getFile())) {
            this.properties.store(os, "save properties");
        } catch (FileNotFoundException e) {
            System.out.println("config.properties file not found.");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readData(String key){
        return this.properties.getProperty(getRealKey(key));
    }

    public String getRealKey(String key){
        return "template-" + TemplateContainer.INSTANCE.getTemplateNum() + "." + key;
    }

}
