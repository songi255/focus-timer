package com.focustimer.focustimer.pages.main;

import com.focustimer.focustimer.model.timer.TimerModel;
import com.focustimer.focustimer.model.timer.TimerObserver;
import com.focustimer.focustimer.model.timer.TimerState;
import com.focustimer.focustimer.service.OverlayService;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

@Slf4j
public class MainController implements Initializable, TimerObserver {
    @FXML Button btnPrev;
    @FXML Button btnNext;
    @FXML Parent mainContainer;
    @FXML Parent headerBar;
    @FXML Parent timerTextView;
    @FXML Parent timerController;
    @FXML Parent timerDiskView;

    private final List<Node> hidingNodes = new Vector<>();

    private final TimerModel timerModel;
    private final OverlayService overlayService;

    @Inject
    public MainController(TimerModel timerModel, OverlayService overlayService) {
        this.timerModel = timerModel;
        timerModel.registerStateObservers(this);
        this.overlayService = overlayService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hidingNodes.addAll(Arrays.asList(btnNext, btnPrev, headerBar, timerTextView, timerController));
    }

    @Override
    public void onTimerStateChanged() {
        TimerState state = timerModel.getState();
        overlayService.setWindow(btnPrev.getScene().getWindow());

        if (state == TimerState.RUNNING){
            log.info("overlay");
            Platform.runLater(this::hideNodesExceptCanvas);
            overlayService.overlay();
        } else {
            log.info("unOverlay");
            Platform.runLater(this::restoreNodesExceptCanvas);
            overlayService.unOverlay();
        }
    }

    @FXML public void handlePrevTemplate(){
        System.out.println("prev template clicked!");
    }

    @FXML public void handleNextTemplate(){
        System.out.println("next template clicked!");
    }

    public void hideNodesExceptCanvas(){
        for(Node node : hidingNodes){
            node.setVisible(false);
            node.setManaged(false);
        }
        GridPane.setColumnIndex(timerDiskView, 0);
        GridPane.setColumnSpan(timerDiskView, 10);
    }

    public void restoreNodesExceptCanvas(){
        for(Node node : hidingNodes){
            node.setVisible(true);
            node.setManaged(true);
        }
        GridPane.setColumnIndex(timerDiskView, 1);
        GridPane.setColumnSpan(timerDiskView, 8);
    }
}