package com.focustimer.focustimer;

import com.focustimer.focustimer.models.TemplateContainer;
import com.focustimer.focustimer.models.timer.TimerModel;
import com.focustimer.focustimer.models.timer.TimerModelObserver;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

public class MainController implements Initializable, TimerModelObserver {
//    @FXML
//    private Label welcomeText;

//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }
    private final TemplateContainer container = TemplateContainer.INSTANCE;
    private final TimerModel timerModel = this.container.getTimerModel();

    @FXML
    private Arc timerArc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timerModel.registerTimeObserver(this);
        timerModel.registerStateObservers(this);

        double radius = 200;

        timerArc.setStartAngle(90);
        //timerArc.setLength(270);

        timerArc.setRadiusX(radius);
        timerArc.setRadiusY(radius);
        timerArc.setCenterX(100);
        timerArc.setCenterY(100);


        timerArc.setType(ArcType.ROUND);
    }

    @Override
    public void onTimerTimeChanged() {
        TimerModelObserver.super.onTimerTimeChanged();
        timerArc.setLength(timerModel.getCurTime()/timerModel.getMaxTime()*360);
    }

    @Override
    public void onTimerStateChanged() {
        TimerModelObserver.super.onTimerStateChanged();
    }
}