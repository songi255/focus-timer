package com.focustimer.focustimer.components.timerTextView;

import com.focustimer.focustimer.model.timer.TimerModel;
import com.focustimer.focustimer.model.timer.TimerObserver;
import com.focustimer.focustimer.model.timer.TimerState;
import com.google.inject.Inject;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class TimerTextViewController implements Initializable, TimerObserver {
    @FXML HBox mainTimerTextBox;
    @FXML Text mainTimerMinute;
    @FXML Text mainTimerColon;
    @FXML Text mainTimerSecond;

    @FXML HBox pomoTimerTextBox;
    @FXML Text pomoTimerMinute;
    @FXML Text pomoTimerColon;
    @FXML Text pomoTimerSecond;


    private final TimerModel timerModel;

    @Inject
    public TimerTextViewController(TimerModel timerModel) {
        this.timerModel = timerModel;
        this.timerModel.registerTimeObserver(this);
        this.timerModel.registerStateObservers(this);
        this.timerModel.registerModeObserver(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawMainTime(timerModel.getStartTime());
        drawPomoTime(timerModel.getPomoStartTime());

        // scroll listner
        mainTimerMinute.setOnScroll(getScrollHandler(false, true));
        mainTimerSecond.setOnScroll(getScrollHandler(false, false));

        pomoTimerMinute.setOnScroll(getScrollHandler(true, true));
        pomoTimerSecond.setOnScroll(getScrollHandler(true, false));
    }

    @Override
    public void onTimerModeChanged() {
        if (timerModel.isPomoMode()){
            drawMainTime(timerModel.getStartTime());
            // colon dimer
            // transition
        } else {
            drawPomoTime(timerModel.getPomoStartTime());
        }
    }

    @Override
    public void onTimerStateChanged() {

    }

    @Override
    public void onTimerTimeChanged() {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300));

        Platform.runLater(this::drawTimeString);
    }

    public EventHandler<ScrollEvent> getScrollHandler(boolean isPomo, boolean isMinute){
        return new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent mouseEvent) {
                if (timerModel.isPomoMode() != isPomo) return;
                if (timerModel.getState() != TimerState.READY) return;

                boolean isUp = mouseEvent.getDeltaY() > 0;
                double d = isMinute ? 60 : 1;
                d *= (isUp ? 1 : -1);

                double next = 0;
                if (isPomo) {
                    next = timerModel.getPomoStartTime() + d;
                    if (next < 0 || next > timerModel.getPomoMaxTime()) return;
                    timerModel.setPomoStartTime(next);
                } else {
                    next = timerModel.getStartTime() + d;
                    if (next < 0 || next > timerModel.getMaxTime()) return;
                    timerModel.setStartTime(next);
                }
                timerModel.setCurTime(next);
            }
        };
    }

    public void drawTimeString(){
        if (timerModel.isPomoMode()){
            drawPomoTime(timerModel.getCurTime());
        } else {
            drawMainTime(timerModel.getCurTime());
        }
    }

    public void drawMainTime(double time){
        mainTimerMinute.setText(formatTimeString(getMinute(time)));
        mainTimerSecond.setText(formatTimeString(getSecond(time)));
    }

    public void drawPomoTime(double time){
        pomoTimerMinute.setText(formatTimeString(getMinute(time)));
        pomoTimerSecond.setText(formatTimeString(getSecond(time)));
    }

    public int getMinute(double time){
        return (int)time / 60;
    }

    public int getSecond(double time){
        return (int)time % 60;
    }

    public String formatTimeString(int time){
        return String.format("%02d", time);
    }
}
