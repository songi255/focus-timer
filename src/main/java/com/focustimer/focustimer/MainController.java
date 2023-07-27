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
import javafx.scene.control.Button;
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
    @FXML Button btnPrev;
    @FXML Button btnNext;
    private TimerModel timerModel;
    private OverlayService overlayService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.timerModel = ModelContainer.CONTAINER.getTimerModel();
        timerModel.registerTimeObserver(this);
        timerModel.registerStateObservers(this);

        this.overlayService = new OverlayService();
    }

    @Override
    public void onTimerTimeChanged() {
        TimerObserver.super.onTimerTimeChanged();
    }

    @Override
    public void onTimerStateChanged() {
        TimerState state = timerModel.getState();
        overlayService.setWindow(btnPrev.getScene().getWindow());

        if (state == TimerState.RUNNING){
            overlayService.overlay();
        } else {
            System.out.println("listen unoverlay");
            overlayService.unOverlay();
        }
    }

    @FXML public void handlePrevTemplate(){
        System.out.println("prev template clicked!");
    }

    @FXML public void handleNextTemplate(){
        System.out.println("next template clicked!");
    }
}