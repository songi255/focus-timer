package com.focustimer.focustimer.utils;

import java.nio.file.FileSystems;

public class PathProvider {
    public static String getAppDataPath(){
        String osName = System.getProperty("os.name").toUpperCase();
        String appDataPath = null;

        if (osName.contains("WINDOWS")){
            appDataPath = System.getenv("APPDATA");
        } else if (osName.contains("LINUX")) {
            appDataPath = System.getProperty("user.home");
        } else {
            return "unsupported OS";
        }

        return appDataPath + FileSystems.getDefault().getSeparator() + "FocusTimer";
    }
}
