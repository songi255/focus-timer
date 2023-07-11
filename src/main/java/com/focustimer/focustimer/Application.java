package com.focustimer.focustimer;

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
        // read setting values
//        Properties properties = new Properties();
//        properties.setProperty("hi!", "hello~");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        // clean resources(file, network,,,)
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("pages/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        // hide title bar
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);

        stage.setOpacity(0.2);

        stage.setScene(scene);
        stage.show();
    }

    public void addTrayIcon(){

    }

    public static void main(String[] args) {
        launch();
    }
}