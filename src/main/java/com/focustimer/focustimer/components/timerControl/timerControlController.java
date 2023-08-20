package com.focustimer.focustimer.components.timerControl;

import com.focustimer.focustimer.model.timer.TimerModel;
import com.focustimer.focustimer.model.timer.TimerObserver;
import com.focustimer.focustimer.model.timer.TimerService;
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

    @FXML ImageView btnStartImg;
    @FXML ImageView btnStopImg;

    @Inject
    public timerControlController(TimerModel timerModel) {
        this.timerModel = timerModel;
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
            timerModel.pauseTimer();
        } else {
            timerModel.startTimer();
        }
    }

    @FXML private void handleStop(){
        timerModel.stopTimer();
    }
}
