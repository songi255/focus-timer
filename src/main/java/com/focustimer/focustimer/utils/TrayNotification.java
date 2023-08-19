package com.focustimer.focustimer.utils;

import java.awt.*;

public class TrayNotification {
    public static void notifyTray(String caption, String text){
        SystemTray tray = SystemTray.getSystemTray();

        Image image = Toolkit.getDefaultToolkit().createImage("some-icon.png");
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Focus Timer");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("System tray icon demo");
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            throw new RuntimeException("UnSupported tray function", e);
        }

        trayIcon.displayMessage(caption, text, TrayIcon.MessageType.INFO);
        
    }
}
