package com.focustimer.focustimer.components;

import com.focustimer.focustimer.models.ModelContainer;
import com.focustimer.focustimer.models.timer.TimerModel;
import com.focustimer.focustimer.models.timer.TimerObserver;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

import java.net.URL;
import java.util.ResourceBundle;

public class TimerDiskViewController implements Initializable, TimerObserver {
    private TimerModel timerModel;

    @FXML
    Canvas timerCanvas;

    private GraphicsContext gc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.timerModel = ModelContainer.CONTAINER.getTimerModel();
        timerModel.registerStateObservers(this);
        timerModel.registerTimeObserver(this);

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
        gc.setFill(Paint.valueOf("white"));
        gc.fillRect(0, 0, 288, 288);
        gc.setFill(Paint.valueOf("red"));
        gc.fillArc(0, 0, 288, 288, 90, timerModel.getCurTime() / timerModel.getMaxTime() * 360, ArcType.ROUND);
        //timerArc.setLength(timerModel.getCurTime() / timerModel.getMaxTime() * 360);
    }
}
