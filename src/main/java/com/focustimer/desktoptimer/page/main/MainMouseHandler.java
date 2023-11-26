package com.focustimer.desktoptimer.page.main;

import com.focustimer.desktoptimer.service.StageService;
import com.focustimer.desktoptimer.service.StageSetting;
import com.focustimer.desktoptimer.viewmodel.TimerViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMouseHandler {
    private final MainController mainController;
    private final StageService stageService;
    private final StageSetting stageSetting;
    private final TimerViewModel timerViewModel;

    private boolean isDragged;
    private double xOffset;
    private double yOffset;

    public MainMouseHandler(MainController mainController) {
        this.mainController = mainController;
        this.stageService = mainController.stageService;
        this.stageSetting = mainController.stageSetting;
        this.timerViewModel = mainController.timerViewModel;
    }

    public void handleHover(boolean isEntering) {
        if (!stageService.isOverlayMode.get() || stageService.isOverlayRunning.get()) {
            mainController.mainContainer.setCursor(Cursor.DEFAULT);
            return;
        }

        Stage stage = mainController.stage;
        mainController.mainContainer.setCursor(isEntering ? Cursor.V_RESIZE : Cursor.DEFAULT);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(stage.opacityProperty(),
                isEntering ?
                        stageSetting.originalOpacity.get() :
                        stageSetting.overlayOpacity.get());
        KeyFrame keyFrame = new KeyFrame(Duration.millis(150), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void handleClick() {
        // unFocus other components
        mainController.mainContainer.requestFocus();

        if (isDragged) {
            isDragged = false;
            return;
        }

        if (!timerViewModel.isTimerRunning.get()) {
            return;
        }
        stageService.flipOverlay();
    }

    public void handleDrag(MouseEvent e) {
        if (stageService.isOverlayRunning.get()) {
            return;
        }
        isDragged = true;
        mainController.stage.setX(e.getScreenX() - xOffset);
        mainController.stage.setY(e.getScreenY() - yOffset);
    }

    public void handlePress(MouseEvent e) {
        xOffset = e.getSceneX();
        yOffset = e.getSceneY();
    }

    public void handleScroll(ScrollEvent e) {
        if (!stageService.isOverlayMode.get() || stageService.isOverlayRunning.get()) {
            return;
        }

        boolean isUp = e.getDeltaY() > 0;
        double gap = 0.05;
        double newOpacity = stageSetting.overlayOpacity.get();
        newOpacity += gap * (isUp ? 1 : -1);
        if (newOpacity > 1) {
            newOpacity = 1;
        }
        if (newOpacity < 0.1) {
            newOpacity = 0.1;
        }
        newOpacity = Math.round(newOpacity * 100) / 100.0;

        stageSetting.overlayOpacity.set(newOpacity);
        mainController.stage.setOpacity(newOpacity);
    }
}
