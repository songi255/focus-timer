package com.focustimer.focustimer.components;

import com.focustimer.focustimer.models.ModelContainer;
import com.focustimer.focustimer.models.timer.TimerModel;
import com.focustimer.focustimer.models.timer.TimerObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class TimerTextViewController implements Initializable, TimerObserver {
    private TimerModel timerModel;

    @FXML Text mainTimerTextView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.timerModel = ModelContainer.CONTAINER.getTimerModel();
        timerModel.registerTimeObserver(this);
    }

    @Override
    public void onTimerTimeChanged() {
        Platform.runLater(()->{
            double curTime = timerModel.getCurTime();
            int minute = (int)curTime / 60;
            int second = (int)curTime % 60;

            mainTimerTextView.setText(minute + ":" + second);
        });
    }
}
