package com.focustimer.focustimer.components;

import com.focustimer.focustimer.model.timer.TimerModel;
import com.focustimer.focustimer.model.timer.TimerObserver;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class TimerDiskViewController implements Initializable, TimerObserver {
    @FXML Canvas timerCanvas;

    private final TimerModel timerModel;
    private final TimerDiskViewDrawer drawer = new TimerDiskViewDrawer();

    @Inject
    public TimerDiskViewController(TimerModel timerModel) {
        this.timerModel = timerModel;
        this.timerModel.registerStateObservers(this);
        this.timerModel.registerTimeObserver(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawer.setGc(timerCanvas.getGraphicsContext2D());
    }

    @Override
    public void onTimerStateChanged() {
        TimerObserver.super.onTimerStateChanged();
    }

    @Override
    public void onTimerTimeChanged() {
        Platform.runLater(()->{
            drawer.clearCanvas();
            drawer.drawArc(Paint.valueOf("red"), timerModel.getCurTime() / timerModel.getMaxTime() * 360);
            drawer.drawScale();
            drawer.drawString(timerModel.getGoalStr());
        });
    }
}
