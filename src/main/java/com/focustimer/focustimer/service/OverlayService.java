/**
 * Provides overlay animation
 *
 * this service modify window's
 *  - opacity
 *  - size
 *  - position
 *
 * @author Dave Shin
 */

package com.focustimer.focustimer.service;

import com.focustimer.focustimer.config.autoscan.ServiceBean;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.stage.Screen;
import javafx.stage.Window;

@ServiceBean
public class OverlayService extends Service<Void> {
    private Window stage;
    private final double RATIO = 0.075;

    // TODO : link with settings
    private double overlayOpacity = 0.2;
    private double overlayWidth = 100;
    private double overlayHeight = 100;
    private double overlayXGap = 200;
    private double overlayYGap = 200;



    // TODO : link with DataManager
    private double originalWidth;
    private double originalXHeight;

    private double originalX;
    private double originalY;

    private boolean isOverlayState = false;

    public void setWindow(Window window){
        this.stage = window;
    }

    public void overlay(){
        if(isOverlayState) return;

        originalWidth = stage.getWidth();
        originalXHeight = stage.getHeight();
        originalX = stage.getX();
        originalY = stage.getY();

        restart();
    }

    public void unOverlay(){
        if(!isOverlayState) return;
        System.out.println("overlay service 1");
        System.out.println(getState());
        System.out.println("overlay service 2");
        restart();
    }

    private Screen getCurScreen(){
        Screen curScreen = Screen.getPrimary();
        for(Screen screen : Screen.getScreens()){
            if (screen.getBounds().contains(stage.getX() + stage.getWidth() / 2, stage.getY() + stage.getHeight() / 2)){
                curScreen = screen;
            }
        }
        return curScreen;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Screen curScreen = getCurScreen();
                double screenWidth = curScreen.getBounds().getWidth();
                double screenHeight = curScreen.getBounds().getHeight();

                // from-to Array for { Opacity, Width, Height, X, Y }
                double[][] arr = {
                        {1, overlayOpacity},
                        {originalWidth, overlayWidth},
                        {originalXHeight, overlayHeight},
                        {originalX, screenWidth - ( overlayWidth + overlayXGap )},
                        {originalY, screenHeight - ( overlayHeight + overlayYGap )}
                };

                if (isOverlayState){
                    for (int i = 0; i < 5; i++) {
                        double temp = arr[i][0];
                        arr[i][0] = arr[i][1];
                        arr[i][1] = temp;
                    }
                }

                boolean allSame = false;
                while(!allSame){
                    allSame = true;
                    for (int i = 0; i < 5; i++) {
                        double from = arr[i][0];
                        double to = arr[i][1];
                        from = (from + to * RATIO) / (RATIO + 1);
                        arr[i][0] = from;

                        if ((to - from) > 1.0) allSame = false;
                    }

                    Platform.runLater(() -> {
                        stage.setOpacity(arr[0][0]);
                        stage.setWidth(arr[1][0]);
                        stage.setHeight(arr[2][0]);
                        stage.setX(arr[3][0]);
                        stage.setY(arr[4][0]);
                    });

                    Thread.sleep(1000 / 60); // 60 fps
                }

//                Platform.runLater(() -> {
//                    // FIXME : stage radius temp
//                    Rectangle rect = new Rectangle(100,100);
//                    rect.setArcHeight(60.0);
//                    rect.setArcWidth(60.0);
//                    timerArc.getScene().getRoot().setClip(rect);
//                    timerArc.getScene().getRoot().setStyle("-fx-background-radius: 30; -fx-border-radius: 30; -fx-background-color: white");
//                });

                isOverlayState = !isOverlayState;
                System.out.println("overay finish");
                return null;
            }
        };
    }
}
