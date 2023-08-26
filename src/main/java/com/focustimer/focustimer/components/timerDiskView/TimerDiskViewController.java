package com.focustimer.focustimer.components.timerDiskView;

import com.focustimer.focustimer.model.overlay.OverlayModel;
import com.focustimer.focustimer.model.timer.TimerState;
import com.focustimer.focustimer.model.timer.TimerModel;
import com.focustimer.focustimer.model.timer.TimerObserver;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class TimerDiskViewController implements Initializable, TimerObserver {
    @FXML CanvasPane timerCanvasContainer;

    private final TimerModel timerModel;
    private final OverlayModel overlayModel;
    private final TimerDiskViewDrawer drawer = new TimerDiskViewDrawer();

    // mouse event
    private double xOffset;
    private double yOffset;

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

        timerCanvas.widthProperty().addListener(obs -> Platform.runLater(this::drawTimer));
        timerCanvas.setOnMousePressed(this::canvasMouseHandler);
        timerCanvas.setOnMouseDragged(this::canvasMouseHandler);

        // dependent on stage width. stage is null at this time.
        Platform.runLater(this::drawTimer);

        TextArea textArea = timerCanvasContainer.getTextArea();
        textArea.setFont(new Font("Inter", 20));
        textArea.setText(timerModel.getGoalStr());
        textArea.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!timerModel.isPomoMode()) {
                timerModel.setGoalStr(newValue);
            }
        });
        textArea.focusedProperty().addListener((obs, oldValue, newValue) -> {
            if (timerModel.getState() != TimerState.READY) timerCanvasContainer.requestFocus();
        });
    }

    @Override
    public void onTimerTimeChanged() {
        Platform.runLater(this::drawTimer);
    }

    @Override
    public void onTimerStateChanged() {
        if (timerModel.getState() == TimerState.READY){
            Platform.runLater(() -> {
                String goal = timerModel.isPomoMode() ? "Rest" : timerModel.getGoalStr();
                timerCanvasContainer.getTextArea().setText(goal);
            });
        }
    }

    public void drawTimer(){
        double curTime = timerModel.getCurTime();
        double maxTime = -1;
        Paint color = null;

        if (timerModel.isPomoMode()){
            maxTime = timerModel.getPomoMaxTime();
            color = Paint.valueOf("4EA1D0");
            drawer.setMainScaleCnt(12);
            drawer.setSubScaleCnt(12 * 5);
        } else {
            maxTime = timerModel.getMaxTime();
            color = Paint.valueOf("D04E4E");
            drawer.setMainScaleCnt(12);
            drawer.setSubScaleCnt(12 * 5);
        }

        drawer.clearCanvas();
        drawer.drawArc(color, curTime / maxTime * 360);
        drawer.drawMainScale();
        if (!overlayModel.isOverlayState()) {
            drawer.drawSubScale();
            drawer.drawScaleNumber(maxTime);
        }
        // drawer.drawGoal(timerModel.getGoalStr());
    }

    public void canvasMouseHandler(MouseEvent e){
        if (timerModel.getState() == TimerState.RUNNING) return;

        Canvas canvas = timerCanvasContainer.getCanvas();
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;
        double mouseX = e.getX();
        double mouseY = e.getY();
        double dx = mouseX - centerX;
        double dy = mouseY - centerY;

        if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
            xOffset = e.getX();
            yOffset = e.getY();
        }
        double offsetRadius = Math.sqrt(Math.pow((xOffset - centerX), 2) + Math.pow((yOffset - centerY), 2));
        double timerArcRadius = (Math.min(canvas.getWidth(), canvas.getHeight()) / 2) * (1 - drawer.getNumRatio()) * (1 - drawer.getScaleRatio());
        if (offsetRadius > timerArcRadius) return;

        double degree = Math.atan2(-dx, -dy) / Math.PI * 180;
        if (degree < 0) degree += 360;

        double snapDegree = 360.0 / drawer.getSubScaleCnt();
        degree = (int)((degree + snapDegree / 2) / snapDegree) * snapDegree;

        if (timerModel.isPomoMode()){
            timerModel.setPomoStartTime(timerModel.getPomoMaxTime() * degree / 360);
            timerModel.setCurTime(timerModel.getPomoStartTime());
        } else {
            timerModel.setStartTime(timerModel.getMaxTime() * degree / 360);
            timerModel.setCurTime(timerModel.getStartTime());
        }
        timerModel.setState(TimerState.READY);

        timerCanvasContainer.requestFocus(); // deprive focus from textArea
        e.consume();
    }
}
