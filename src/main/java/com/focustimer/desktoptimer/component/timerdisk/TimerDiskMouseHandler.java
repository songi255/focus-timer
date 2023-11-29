package com.focustimer.desktoptimer.component.timerdisk;

import com.focustimer.desktoptimer.viewmodel.TimerViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class TimerDiskMouseHandler {

    private Canvas canvas;
    private final TimerViewModel timerViewModel;
    private TimerDiskSetting setting;

    private final DoubleProperty centerX = new SimpleDoubleProperty();
    private final DoubleProperty centerY = new SimpleDoubleProperty();

    private double xOffset;
    private double yOffset;

    public TimerDiskMouseHandler(TimerViewModel timerViewModel) {
        this.timerViewModel = timerViewModel;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        centerX.bind(Bindings.divide(canvas.widthProperty(), 2));
        centerY.bind(Bindings.divide(canvas.heightProperty(), 2));
    }

    public void setTimerDiskSetting(TimerDiskSetting setting){
        this.setting = setting;
    }

    public void canvasMouseHandler(MouseEvent e){
        if (timerViewModel.isTimerRunning.get()) return;

        double mouseX = e.getX();
        double mouseY = e.getY();
        double dx = mouseX - centerX.get();
        double dy = mouseY - centerY.get();

        if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
            xOffset = e.getX();
            yOffset = e.getY();
        }

        double offsetRadius = Math.sqrt(Math.pow((xOffset - centerX.get()), 2)
                + Math.pow((yOffset - centerY.get()), 2));
        double timerArcRadius = (Math.min(canvas.getWidth(), canvas.getHeight()) / 2)
                * (1 - setting.numberSizeRatio.get())
                * (1 - setting.scaleSizeRatio.get());
        if (offsetRadius > timerArcRadius) return;

        double degree = Math.atan2(-dx, -dy) / Math.PI * 180;
        if (degree < 0) degree += 360;

        double snapDegree = 360.0 / setting.subScaleCount.get();
        degree = (int)((degree + snapDegree / 2) / snapDegree) * snapDegree;

        timerViewModel.startTime.set((long) (timerViewModel.maxTime.get() * degree / 360));
        timerViewModel.curTime.set(timerViewModel.startTime.get());

        // deprive focus from other nodes
        canvas.requestFocus();
        e.consume();
    }

    public void handleMouseScrollEvent(ScrollEvent e){
        if (timerViewModel.isTimerRunning.get()) {
            return;
        }

        boolean isUp = e.getDeltaY() > 0;
        long deltaTime = 60 * (isUp ? 1 : -1);

        long next = timerViewModel.startTime.get() + deltaTime;
        if (next < 0 || next > timerViewModel.maxTime.get()) {
            return;
        }
        timerViewModel.curTime.set(next);
        timerViewModel.startTime.set(next);
    }
}
