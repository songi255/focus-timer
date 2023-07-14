package com.focustimer.focustimer;

import com.focustimer.focustimer.models.Assembler;
import com.focustimer.focustimer.models.ModelContainer;
import com.focustimer.focustimer.models.template.TemplateModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void init() throws Exception {
        super.init();

        Assembler.assemble();

        ModelContainer container = ModelContainer.CONTAINER;
        TemplateModel templateModel = container.getTemplateModel();

        templateModel.setTemplateNum(1);

        //container.getTimerModel().start();
    }

    @Override
    public void stop() throws Exception {
        // clean resources(file, network,,,)
        super.stop();
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