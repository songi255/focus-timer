package com.focustimer.focustimer.components.timerDiskView;

import com.focustimer.focustimer.model.overlay.OverlayModel;
import com.focustimer.focustimer.model.timer.TimerState;
import com.focustimer.focustimer.utils.CanvasPane;
import com.focustimer.focustimer.model.timer.TimerModel;
import com.focustimer.focustimer.model.timer.TimerObserver;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class TimerDiskViewController implements Initializable, TimerObserver {
    @FXML CanvasPane timerCanvasContainer;

    private final TimerModel timerModel;
    private final OverlayModel overlayModel;
    private final TimerDiskViewDrawer drawer = new TimerDiskViewDrawer();

    @Inject
    public TimerDiskViewController(TimerModel timerModel, OverlayModel overlayModel) {
        this.timerModel = timerModel;
        this.timerModel.registerStateObservers(this);
        this.timerModel.registerTimeObserver(this);
        this.overlayModel = overlayModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Canvas timerCanvas = timerCanvasContainer.getCanvas();
        drawer.setGc(timerCanvas.getGraphicsContext2D());

        timerCanvas.widthProperty().addListener(obs -> drawTimer());
        timerCanvas.setOnMouseClicked(this::canvasMouseHandler);
        timerCanvas.setOnMouseDragged(this::canvasMouseHandler);
    }

    @Override
    public void onTimerTimeChanged() {
        Platform.runLater(this::drawTimer);
    }

    public void drawTimer(){
        double curTime = timerModel.getCurTime();
        double maxTime = -1;
        Paint color = null;

        if (timerModel.isPomoMode()){
            maxTime = timerModel.getPomoMaxTime();
            color = Paint.valueOf("blue");
        } else {
            maxTime = timerModel.getMaxTime();
            color = Paint.valueOf("D04E4E");
        }

        drawer.clearCanvas();
        drawer.drawArc(color, curTime / maxTime * 360);
        drawer.drawMainScale();
        if (!overlayModel.isOverlayState()) {
            drawer.drawSubScale();
            drawer.drawScaleNumber();
        }
        drawer.drawGoal(timerModel.getGoalStr());
    }

    public void canvasMouseHandler(MouseEvent e){
        if (timerModel.getState() == TimerState.RUNNING) return;

        Canvas canvas = timerCanvasContainer.getCanvas();
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;
        double mouseX = e.getX();
        double mouseY = e.getY();

        double degree = Math.atan2(-(mouseX - centerX), -(mouseY - centerY)) / Math.PI * 180;
        if (degree < 0) degree += 360;

        if (timerModel.isPomoMode()){
            timerModel.setPomoStartTime(timerModel.getPomoMaxTime() * degree / 360);
            timerModel.setCurTime(timerModel.getPomoStartTime());
        } else {
            timerModel.setStartTime(timerModel.getMaxTime() * degree / 360);
            timerModel.setCurTime(timerModel.getStartTime());
        }
        timerModel.setState(TimerState.READY);
    }
}
