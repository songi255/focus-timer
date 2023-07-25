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

    @FXML ImageView btnStart;
    @FXML ImageView btnStop;

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
                btnStart.setImage(new Image("../resources/com/focustimer/focustimer/icons/play.png"));
            });
        } else {
            Platform.runLater(() -> {
                btnStart.setImage(new Image("../resources/com/focustimer/focustimer/icons/pause.png"));
            });
        }
    }

    @FXML private void handleStart(){
        timerModel.start();
        System.out.println("start!");
    }

    @FXML private void handleStop(){
        timerModel.stop();
        System.out.println("stop!");
    }
}
