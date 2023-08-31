package com.focustimer.focustimer.utils;

import java.nio.file.FileSystems;

public class PathProvider {
    public static String getAppDataPath(){
        String osName = System.getProperty("os.name").toUpperCase();
        String appDataPath = null;
        String folderPath = null;

        if (osName.contains("WINDOWS")){
            appDataPath = System.getenv("APPDATA");
            folderPath = "FocusTimer";
        } else if (osName.contains("LINUX") || osName.contains("MAC")) {
            appDataPath = System.getProperty("user.home");
            folderPath = ".FocusTimer";
        } else {
            return "unsupported OS";
        }

        return appDataPath + FileSystems.getDefault().getSeparator() + folderPath;
    }
}
