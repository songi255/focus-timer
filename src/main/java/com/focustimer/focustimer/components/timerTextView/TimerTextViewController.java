package com.focustimer.focustimer.components.timerTextView;

import com.focustimer.focustimer.model.timer.TimerModel;
import com.focustimer.focustimer.model.timer.TimerObserver;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class TimerTextViewController implements Initializable, TimerObserver {
    @FXML Text mainTimerTextView;

    private final TimerModel timerModel;

    @Inject
    public TimerTextViewController(TimerModel timerModel) {
        this.timerModel = timerModel;
        this.timerModel.registerTimeObserver(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
