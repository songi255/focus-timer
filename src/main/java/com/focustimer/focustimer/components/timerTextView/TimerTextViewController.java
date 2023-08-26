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
import javafx.scene.Cursor;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
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
        setMainFont(new Font("Inter", 40), Paint.valueOf("black"));
        setMainText(timerModel.getStartTime());
        setPomoFont(new Font("Inter", 20), Paint.valueOf("D9D9D9"));
        setPomoText(timerModel.getPomoStartTime());

        mainTimerMinute.setCursor(Cursor.V_RESIZE);
        mainTimerSecond.setCursor(Cursor.V_RESIZE);
        pomoTimerMinute.setCursor(Cursor.V_RESIZE);
        pomoTimerSecond.setCursor(Cursor.V_RESIZE);

        // scroll listner
        mainTimerMinute.setOnScroll(getScrollHandler(false, true));
        mainTimerSecond.setOnScroll(getScrollHandler(false, false));

        pomoTimerMinute.setOnScroll(getScrollHandler(true, true));
        pomoTimerSecond.setOnScroll(getScrollHandler(true, false));
    }

    @Override
    public void onTimerModeChanged() {
        Font smallFont = new Font("Inter", 20);
        Font bigFont = new Font("Inter", 40);
        Paint smallColor = Paint.valueOf("D9D9D9");
        Paint bigColor = Paint.valueOf("black");
        Platform.runLater(() -> {
            if (timerModel.isPomoMode()){
                setMainFont(smallFont, smallColor);
                setPomoFont(bigFont, bigColor);
                setMainText(timerModel.getStartTime());
            } else {
                setMainFont(bigFont, bigColor);
                setPomoFont(smallFont, smallColor);
                setPomoText(timerModel.getPomoStartTime());
            }
        });
    }

    @Override
    public void onTimerTimeChanged() {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300));

        Platform.runLater(this::updateText);
    }

    private EventHandler<ScrollEvent> getScrollHandler(boolean isPomo, boolean isMinute){
        return new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent mouseEvent) {
                TimerState state = timerModel.getState();
                if (!(state == TimerState.READY || state == TimerState.STOP || state == TimerState.FINISH)) return;

                boolean isUp = mouseEvent.getDeltaY() > 0;
                double d = isMinute ? 60 : 1;
                d *= (isUp ? 1 : -1);

                double next = 0;
                if (isPomo) {
                    next = timerModel.getPomoStartTime() + d;
                    if (next < 0 || next > timerModel.getPomoMaxTime()) return;
                    timerModel.setPomoStartTime(next);
                    setPomoText(next);
                } else {
                    next = timerModel.getStartTime() + d;
                    if (next < 0 || next > timerModel.getMaxTime()) return;
                    timerModel.setStartTime(next);
                    setMainText(next);
                }
                if (timerModel.isPomoMode() == isPomo) {
                    timerModel.setCurTime(next);
                }
            }
        };
    }

    public void updateText(){
        if (timerModel.isPomoMode()){
            setPomoText(timerModel.getCurTime());
        } else {
            setMainText(timerModel.getCurTime());
        }
    }

    public void setMainText(double time){
        mainTimerMinute.setText(formatTimeString(getMinute(time)));
        mainTimerSecond.setText(formatTimeString(getSecond(time)));
    }

    public void setMainFont(Font font, Paint color){
        mainTimerMinute.setFont(font);
        mainTimerMinute.setFill(color);
        mainTimerColon.setFont(font);
        mainTimerColon.setFill(color);
        mainTimerSecond.setFont(font);
        mainTimerSecond.setFill(color);
    }

    public void setPomoText(double time){
        pomoTimerMinute.setText(formatTimeString(getMinute(time)));
        pomoTimerSecond.setText(formatTimeString(getSecond(time)));
    }

    public void setPomoFont(Font font, Paint color){
        pomoTimerMinute.setFont(font);
        pomoTimerMinute.setFill(color);
        pomoTimerColon.setFont(font);
        pomoTimerColon.setFill(color);
        pomoTimerSecond.setFont(font);
        pomoTimerSecond.setFill(color);
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
