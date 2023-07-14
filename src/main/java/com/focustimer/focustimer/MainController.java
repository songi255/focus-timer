package com.focustimer.focustimer;

import com.focustimer.focustimer.models.ModelContainer;
import com.focustimer.focustimer.models.template.TemplateModel;
import com.focustimer.focustimer.models.timer.TimerModel;
import com.focustimer.focustimer.models.timer.TimerObserver;
import com.focustimer.focustimer.models.timer.TimerState;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, TimerObserver {
//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }
    private TimerModel timerModel;

    @FXML
    private Arc timerArc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.timerModel = ModelContainer.CONTAINER.getTimerModel();
        timerModel.registerTimeObserver(this);
        timerModel.registerStateObservers(this);

        double radius = 200;

        timerArc.setStartAngle(90);
        timerArc.setLength(timerModel.getCurTime() / timerModel.getMaxTime() * 360);

        timerArc.setRadiusX(radius);
        timerArc.setRadiusY(radius);
        timerArc.setCenterX(100);
        timerArc.setCenterY(100);


        timerArc.setType(ArcType.ROUND);
    }

    @Override
    public void onTimerTimeChanged() {
        TimerObserver.super.onTimerTimeChanged();
        timerArc.setLength(timerModel.getCurTime() / timerModel.getMaxTime() * 360);
    }

    @Override
    public void onTimerStateChanged() {
        TimerState state = timerModel.getState();
        if (state == TimerState.RUNNING){
            // opacity
            // size
            // position
        }
    }
}