package com.focustimer.desktoptimer.page.main;

import com.focustimer.desktoptimer.common.Inject;
import com.focustimer.desktoptimer.config.ApplicationSetting;
import com.focustimer.desktoptimer.service.StageService;
import com.focustimer.desktoptimer.service.StageSetting;
import com.focustimer.desktoptimer.util.Notification;
import com.focustimer.desktoptimer.util.StageShownEvent;
import com.focustimer.desktoptimer.viewmodel.TimerViewModel;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static com.focustimer.desktoptimer.util.NewValueListener.listen;

public class MainController implements Initializable {
    @FXML
    GridPane mainContainer;
    @FXML
    Parent headerBar;
    @FXML
    Parent timerTextView;
    @FXML
    Parent timerControl;
    @FXML
    Parent timerDiskView;
    @FXML
    Parent finishTime;
    final List<Node> hidingNodes = new Vector<>();

    Stage stage;

    final TimerViewModel timerViewModel;
    final StageService stageService;
    final StageSetting stageSetting;
    final MainMouseHandler mouseHandler;

    @Inject
    public MainController(TimerViewModel timerViewModel, StageService stageService, StageSetting stageSetting) {
        this.timerViewModel = timerViewModel;
        this.stageService = stageService;
        this.stageSetting = stageSetting;
        this.mouseHandler = new MainMouseHandler(this);

        timerViewModel.isTimerRunning.addListener(listen(isRunning -> {
            if (isRunning) {
                stageService.overlay();
            } else {
                stageService.unOverlay();
                if (ApplicationSetting.alarmSound.get()){
                    Notification.playAlertSound();
                }
                //Notification.showMessage(stage, "timer done");
            }
        }));

        stageService.isOverlayMode.addListener(listen(isOverlay -> {
            if (isOverlay) {
                setupOverlayStage();
            } else {
                restoreOverlayStage();
            }
        }));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hidingNodes.addAll(List.of(timerTextView, timerControl, headerBar, finishTime));
        // detect stage shown
        mainContainer.addEventHandler(StageShownEvent.STAGE_SHOWN, event -> {
            this.stage = (Stage) mainContainer.getScene().getWindow();
            stageService.setStage(stage);
            setupNormalStage();
            stage.fullScreenProperty().addListener(listen(isFullScreen -> {
                if (isFullScreen) {
                    setupFullScreenStage();
                } else {
                    setupNormalStage();
                }
            }));
        });
    }

    public void setupOverlayStage() {
        for (Node node : hidingNodes) {
            node.setVisible(false);
            node.setManaged(false);
        }
        GridPane.setColumnIndex(timerDiskView, 0);
        GridPane.setColumnSpan(timerDiskView, 10);
        mainContainer.setStyle("-fx-background-radius: 30; -fx-border-radius: 30; -fx-background-insets: 20;");
    }

    public void restoreOverlayStage() {
        for (Node node : hidingNodes) {
            node.setVisible(true);
            node.setManaged(true);
        }
        GridPane.setColumnIndex(timerDiskView, 1);
        GridPane.setColumnSpan(timerDiskView, 8);
        mainContainer.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-background-insets: 20;");
    }

    public void setupFullScreenStage() {
        mainContainer.setStyle("-fx-background-radius: 0; -fx-border-radius: 0; -fx-background-insets: 0;");
        mainContainer.setPadding(Insets.EMPTY);
        mainContainer.setEffect(null);
    }

    public void setupNormalStage() {
        mainContainer.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-background-insets: 20;");
        mainContainer.setPadding(new Insets(20));
        mainContainer.setEffect(new DropShadow(10, 2, 2, Color.valueOf("Gray")));
    }

    @FXML public void mainContainerClickHandler(){
        mouseHandler.handleClick();
    }

    @FXML public void mainContainerDragHandler(MouseEvent e){
        mouseHandler.handleDrag(e);
    }

    @FXML public void mainContainerPressHandler(MouseEvent e){
        mouseHandler.handlePress(e);
    }

    @FXML public void mainContainerMouseEnterHandler(MouseEvent e) {
        mouseHandler.handleHover(true);
    }

    @FXML public void mainContainerMouseExitHandler(MouseEvent e) {
        mouseHandler.handleHover(false);
    }

    @FXML public void mainContainerScrollHandler(ScrollEvent e) {
        mouseHandler.handleScroll(e);
    }
}