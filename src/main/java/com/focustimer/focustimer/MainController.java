package com.focustimer.focustimer;

import com.focustimer.focustimer.models.ModelContainer;
import com.focustimer.focustimer.models.template.TemplateModel;
import com.focustimer.focustimer.models.timer.TimerModel;
import com.focustimer.focustimer.models.timer.TimerObserver;
import com.focustimer.focustimer.models.timer.TimerState;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Window;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Spliterator;
import java.util.Timer;
import java.util.TimerTask;

public class MainController implements Initializable, TimerObserver {
    private TimerModel timerModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.timerModel = ModelContainer.CONTAINER.getTimerModel();
        timerModel.registerTimeObserver(this);
        timerModel.registerStateObservers(this);
    }

    @Override
    public void onTimerTimeChanged() {
        TimerObserver.super.onTimerTimeChanged();
    }

    @Override
    public void onTimerStateChanged() {
//        TimerState state = timerModel.getState();
//        if (state == TimerState.RUNNING){
//            // opacity
//            // size
//            // position
//
//            Window stage = timerArc.getScene().getWindow();
//            Screen curScreen = Screen.getPrimary();
//            for(Screen screen : Screen.getScreens()){
//                if (screen.getBounds().contains(stage.getX() + stage.getWidth() / 2, stage.getY() + stage.getHeight() / 2)){
//                    curScreen = screen;
//                }
//            }
//
//            double screenWidth = curScreen.getBounds().getWidth();
//            double screenHeight = curScreen.getBounds().getHeight();
//
//            // FIXME : use basic Thread
//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//                final long startTimeMillis = System.currentTimeMillis();
//                final double toOpacity = 0.2;
//                final double toWidth = 100;
//                final double toHeight = 100;
//                final double toX = screenWidth - toWidth - 100;
//                final double toY = screenHeight - toHeight - 100;
//
//                public void onTimeout(){
//                    Platform.runLater(() -> {
//                        stage.setOpacity(toOpacity);
//                        stage.setWidth(toWidth);
//                        stage.setHeight(toHeight);
//                        stage.setX(toX);
//                        stage.setY(toY);
//
//                        // FIXME : stage radius temp
//                        Rectangle rect = new Rectangle(100,100);
//                        rect.setArcHeight(60.0);
//                        rect.setArcWidth(60.0);
//                        timerArc.getScene().getRoot().setClip(rect);
//                        timerArc.getScene().getRoot().setStyle("-fx-background-radius: 30; -fx-border-radius: 30; -fx-background-color: white");
//
//                    });
//                }
//
//                public double getNextValue(double from, double to){
//                    double ratio = 0.075;
//                    return (from + to * ratio) / (ratio + 1);
//                }
//
//                @Override
//                public void run() {
//                    Platform.runLater(() -> {
//                        stage.setOpacity(getNextValue(stage.getOpacity(), toOpacity));
//                        stage.setWidth(getNextValue(stage.getWidth(), toWidth));
//                        stage.setHeight(getNextValue(stage.getHeight(), toHeight));
//                        stage.setX(getNextValue(stage.getX(), toX));
//                        stage.setY(getNextValue(stage.getY(), toY));
//                    });
//
//                    if ((System.currentTimeMillis() - startTimeMillis) > 500){
//                        timer.cancel();
//                        onTimeout();
//                    }
//                }
//            }, 0, 1000 / 144);
//        }
    }
}