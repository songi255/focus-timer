package com.focustimer.focustimer.utils;

import javafx.stage.Screen;
import javafx.stage.Stage;

public class ScreenUtil {
    public static Screen getCurScreen(Stage stage){
        Screen curScreen = Screen.getPrimary();
        for(Screen screen : Screen.getScreens()){
            if (screen.getBounds().contains(stage.getX() + stage.getWidth() / 2, stage.getY() + stage.getHeight() / 2)){
                curScreen = screen;
            }
        }
        return curScreen;
    }
}
