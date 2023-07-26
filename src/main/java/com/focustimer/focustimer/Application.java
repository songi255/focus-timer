package com.focustimer.focustimer;

import com.focustimer.focustimer.models.Assembler;
import com.focustimer.focustimer.models.ModelContainer;
import com.focustimer.focustimer.models.template.TemplateModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Application extends javafx.application.Application {
    final static int APP_DEFAULT_WIDTH = 360;
    final static int APP_DEFAULT_HEIGHT = 480;

    @Override
    public void init() throws Exception {
        super.init();

        new Assembler().assemble();

        ModelContainer container = ModelContainer.CONTAINER;
        TemplateModel templateModel = container.getTemplateModel();

        templateModel.setTemplateNum(1);

    }

    @Override
    public void stop() throws Exception {
        // clean resources(file, network,,,)
        super.stop();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("pages/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), APP_DEFAULT_WIDTH, APP_DEFAULT_HEIGHT);
        //scene.setFill(Color.TRANSPARENT);

        //stage.initStyle(StageStyle.UNDECORATED);
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(true);
        stage.setAlwaysOnTop(true);

        stage.setScene(scene);
        stage.show();
    }

    public void addTrayIcon(){

    }

    public static void main(String[] args) {
        launch();
    }
}