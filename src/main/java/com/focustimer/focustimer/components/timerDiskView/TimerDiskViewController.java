package com.focustimer.focustimer.components.timerDiskView;

import com.focustimer.focustimer.utils.CanvasPane;
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
    @FXML CanvasPane timerCanvasContainer;

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
        Canvas timerCanvas = timerCanvasContainer.getCanvas();
        drawer.setGc(timerCanvas.getGraphicsContext2D());

        timerCanvas.widthProperty().addListener(obs -> drawTimer());
    }

    @Override
    public void onTimerTimeChanged() {
        Platform.runLater(this::drawTimer);
    }

    public void drawTimer(){
        drawer.clearCanvas();
        drawer.drawArc(Paint.valueOf("D04E4E"), timerModel.getCurTime() / timerModel.getMaxTime() * 360);
        drawer.drawScale();
        drawer.drawScaleNumber();
        drawer.drawGoal(timerModel.getGoalStr());
    }
}
