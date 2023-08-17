package com.focustimer.focustimer.components.timerTextView;

import com.focustimer.focustimer.model.timer.TimerModel;
import com.focustimer.focustimer.model.timer.TimerObserver;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class TimerTextViewController implements Initializable, TimerObserver {
    @FXML Text mainTimerTextView;
    @FXML Text pomoTimerTextView;

    private final TimerModel timerModel;

    @Inject
    public TimerTextViewController(TimerModel timerModel) {
        this.timerModel = timerModel;
        this.timerModel.registerTimeObserver(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainTimerTextView.setFont(new Font("Helvetica", 40));
        pomoTimerTextView.setFont(new Font("Helvetica", 20));

        // temp
        pomoTimerTextView.setText("05:00");
    }

    @Override
    public void onTimerTimeChanged() {
        Platform.runLater(this::drawTimeString);
    }

    public void drawTimeString(){
        double curTime = timerModel.getCurTime();
        int minute = (int)curTime / 60;
        int second = (int)curTime % 60;

        Text target = timerModel.isPomoMode() ? pomoTimerTextView : mainTimerTextView;
        target.setText(String.format("%02d:%02d", minute, second));
    }
}
