package com.focustimer.focustimer.pages.main;

import com.focustimer.focustimer.model.overlay.OverlayModel;
import com.focustimer.focustimer.model.timer.TimerModel;
import com.focustimer.focustimer.model.timer.TimerObserver;
import com.focustimer.focustimer.model.timer.TimerState;
import com.focustimer.focustimer.model.overlay.OverlayService;
import com.focustimer.focustimer.utils.TrayNotification;
import com.google.inject.Inject;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    private final OverlayModel overlayModel;

    @Inject
    public MainController(TimerModel timerModel, OverlayModel overlayModel) {
        this.timerModel = timerModel;
        timerModel.registerStateObservers(this);
        this.overlayModel = overlayModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hidingNodes.addAll(Arrays.asList(btnNext, btnPrev, headerBar, timerTextView, timerController));
        mainContainer.hoverProperty().addListener((obs, oldValue, newValue) -> {
            if (!overlayModel.isOverlayState()) return;
            if (overlayModel.isServiceRunning()) return;

            Stage stage = (Stage) mainContainer.getScene().getWindow();

            Timeline timeline = new Timeline();
            KeyValue keyValue = new KeyValue(stage.opacityProperty(), newValue ? 1 : overlayModel.getOverlayOpacity());
            KeyFrame keyFrame = new KeyFrame(Duration.millis(150),keyValue);
            timeline.getKeyFrames().add(keyFrame);

            timeline.play();
        });
    }

    @Override
    public void onTimerStateChanged() {
        TimerState state = timerModel.getState();

        if (overlayModel.getStage() == null) {
            overlayModel.setStage((Stage) btnPrev.getScene().getWindow());
        }

        if (state == TimerState.RUNNING){
            log.info("overlay");
            overlayModel.overlay();
            Platform.runLater(this::hideNodesExceptCanvas);
        } else {
            log.info("unOverlay");
            overlayModel.unOverlay();
            Platform.runLater(this::restoreNodesExceptCanvas);
        }

        if (state == TimerState.FINISH){
            TrayNotification.notifyTray("Time done", "test");
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

    @FXML public void mainContainerClickHandler(){

    }
}