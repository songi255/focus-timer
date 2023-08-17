package com.focustimer.focustimer;

import com.focustimer.focustimer.config.AppModule;
import com.focustimer.focustimer.config.store.TemplateModel;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Application extends javafx.application.Application {
    final static int APP_DEFAULT_WIDTH = 360;
    final static int APP_DEFAULT_HEIGHT = 480;

    @Override
    public void init() throws Exception {
        Font.loadFont(getClass().getResourceAsStream("fonts/Helvetica.ttf"), 11);

        super.init();
    }

    @Override
    public void stop() throws Exception {
        // clean resources(file, network,,,)
        super.stop();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Injector injector = Guice.createInjector(new AppModule());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(injector::getInstance);
        fxmlLoader.setLocation(getClass().getResource("pages/main/main.fxml"));

        Scene scene = new Scene(fxmlLoader.load(getClass().getResourceAsStream("pages/main/main.fxml")), APP_DEFAULT_WIDTH, APP_DEFAULT_HEIGHT);
        scene.setFill(Color.TRANSPARENT);

        Rectangle rect = new Rectangle();
        rect.widthProperty().bind(scene.widthProperty());
        rect.heightProperty().bind(scene.heightProperty());
        rect.setArcHeight(30.0);
        rect.setArcWidth(30.0);
        scene.getRoot().setClip(rect);

        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(true);
        stage.setAlwaysOnTop(true);

        stage.setScene(scene);
        stage.show();

        // temp
        injector.getInstance(TemplateModel.class).setTemplateNum(1);
    }

    public void addTrayIcon(){
        // https://jinseongsoft.tistory.com/166
    }

    public static void main(String[] args) {
        launch();
    }
}