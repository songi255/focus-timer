package com.focustimer.focustimer.components;

import com.focustimer.focustimer.models.ModelContainer;
import com.focustimer.focustimer.models.timer.TimerModel;
import com.focustimer.focustimer.models.timer.TimerObserver;
import com.focustimer.focustimer.models.timer.TimerState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class timerControlController implements Initializable, TimerObserver {
    private TimerModel timerModel;

    @FXML ImageView btnStartImg;
    @FXML ImageView btnStopImg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.timerModel = ModelContainer.CONTAINER.getTimerModel();
        timerModel.registerStateObservers(this);
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
            timerModel.pause();
        } else {
            timerModel.start();
        }
    }

    @FXML private void handleStop(){
        timerModel.stop();
    }
}
