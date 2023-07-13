package com.focustimer.focustimer;

import com.focustimer.focustimer.models.TemplateContainer;
import com.focustimer.focustimer.models.TemplateDataManager;
import com.focustimer.focustimer.models.timer.TimerModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Properties;

public class Application extends javafx.application.Application {
    @Override
    public void init() throws Exception {
        super.init();

        // DI
        TimerModel timerModel = new TimerModel();
        TemplateDataManager templateDataManager = new TemplateDataManager();

        TemplateContainer container = TemplateContainer.INSTANCE;
        container.setTimerModel(timerModel);
        container.setTemplateDataManager(templateDataManager);
        container.registerObservers(timerModel);

        container.setTemplateNum(1);

        templateDataManager.saveData("hi", "hihi");

        timerModel.start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        // clean resources(file, network,,,)
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("pages/main.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Scene scene = new Scene(fxmlLoader.load(), 1280, 960);

        // hide title bar
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(true);

        stage.setAlwaysOnTop(true);
        //stage.setOpacity(0.2);

        stage.setScene(scene);
        stage.show();
    }

    public void addTrayIcon(){

    }

    public static void main(String[] args) {
        launch();
    }
}