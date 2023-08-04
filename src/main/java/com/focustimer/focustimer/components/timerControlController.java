package com.focustimer.focustimer.components;

import com.focustimer.focustimer.model.timer.TimerModel;
import com.focustimer.focustimer.model.timer.TimerObserver;
import com.focustimer.focustimer.service.TimerService;
import com.focustimer.focustimer.model.timer.TimerState;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class timerControlController implements Initializable, TimerObserver {
    private final TimerModel timerModel;
    private final TimerService timerService;

    @FXML ImageView btnStartImg;
    @FXML ImageView btnStopImg;

    @Inject
    public timerControlController(TimerModel timerModel, TimerService timerService) {
        this.timerModel = timerModel;
        this.timerService = timerService;

        this.timerModel.registerStateObservers(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onTimerStateChanged() {
        TimerState state = timerModel.getState();
        if (state == TimerState.RUNNING){
            Platform.runLater(() -> {
                btnStartImg.setImage(new Image(getClass().getResource("/com/focustimer/focustimer/icons/pause.png").toExternalForm()));
            });
        } else {
            Platform.runLater(() -> {
                btnStartImg.setImage(new Image(getClass().getResource("/com/focustimer/focustimer/icons/play.png").toExternalForm()));
            });
        }
    }

    @FXML private void handleStart(){
        TimerState state = timerModel.getState();
        if (state == TimerState.RUNNING){
            timerService.pauseTimer();
        } else {
            timerService.startTimer();
        }
    }

    @FXML private void handleStop(){
        timerService.stopTimer();
    }
}
