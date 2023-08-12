package com.focustimer.focustimer.pages.main;

import com.focustimer.focustimer.model.timer.TimerModel;
import com.focustimer.focustimer.model.timer.TimerObserver;
import com.focustimer.focustimer.model.timer.TimerState;
import com.focustimer.focustimer.service.OverlayService;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController implements TimerObserver {
    @FXML Button btnPrev;
    @FXML Button btnNext;
    private final TimerModel timerModel;
    private final OverlayService overlayService;

    @Inject
    public MainController(TimerModel timerModel, OverlayService overlayService) {
        this.timerModel = timerModel;
        timerModel.registerStateObservers(this);
        this.overlayService = overlayService;
    }

    @Override
    public void onTimerStateChanged() {
        TimerState state = timerModel.getState();
        overlayService.setWindow(btnPrev.getScene().getWindow());

        if (state == TimerState.RUNNING){
            overlayService.overlay();
        } else {
            System.out.println("unOverlay");
            overlayService.unOverlay();
        }
    }

    @FXML public void handlePrevTemplate(){
        System.out.println("prev template clicked!");
    }

    @FXML public void handleNextTemplate(){
        System.out.println("next template clicked!");
    }
}