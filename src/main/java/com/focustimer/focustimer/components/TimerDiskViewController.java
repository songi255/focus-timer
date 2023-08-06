package com.focustimer.focustimer.components;

import com.focustimer.focustimer.model.timer.TimerEvent;
import com.focustimer.focustimer.model.timer.TimerModel;
import com.focustimer.focustimer.model.timer.TimerObserver;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;

import java.net.URL;
import java.util.ResourceBundle;

public class TimerDiskViewController implements Initializable, TimerObserver {
    @FXML Canvas timerCanvas;

    private final TimerModel timerModel;
    private GraphicsContext gc;

    @Inject
    public TimerDiskViewController(TimerModel timerModel) {
        this.timerModel = timerModel;
        this.timerModel.registerStateObservers(this);
        this.timerModel.registerTimeObserver(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double radius = 144;

        gc = timerCanvas.getGraphicsContext2D();
        gc.strokeRect(0, 0, 288, 288);
        gc.setFill(Paint.valueOf("red"));
        gc.fillArc(0, 0, 288, 288, 90, timerModel.getCurTime() / timerModel.getMaxTime() * 360, ArcType.ROUND);
    }

    @Override
    public void onTimerStateChanged() {
        TimerObserver.super.onTimerStateChanged();
    }

    @Override
    public void onTimerTimeChanged() {
        Platform.runLater(()->{
            // clear
            gc.clearRect(0, 0, 288, 288);

            // draw arc
            gc.setEffect(new DropShadow());
            gc.setFill(Paint.valueOf("red"));
            gc.fillArc(0, 0, 288, 288, 90, timerModel.getCurTime() / timerModel.getMaxTime() * 360, ArcType.ROUND);
            gc.setEffect(null);

            // draw scale
            gc.setFill(Paint.valueOf("black"));
            for (int i = 0; i < 12; i++) {
                gc.translate(144, 144);
                gc.rotate(30);
                gc.translate(-144, -144);
                gc.fillRect(144-2,0, 4, 30);
            }

            for (int i = 0; i < 12 * 5; i++) {
                gc.translate(144, 144);
                gc.rotate(30 / 5);
                gc.translate(-144, -144);
                gc.fillRect(144-1,3, 2, 24);
            }
        });
    }
}
