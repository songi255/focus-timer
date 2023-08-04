package com.focustimer.focustimer;

import com.focustimer.focustimer.config.AppModule;
import com.focustimer.focustimer.model.template.TemplateModel;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    final static int APP_DEFAULT_WIDTH = 360;
    final static int APP_DEFAULT_HEIGHT = 480;

    @Override
    public void init() throws Exception {
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
        // temp
        injector.getInstance(TemplateModel.class).setTemplateNum(1);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(injector::getInstance);
        fxmlLoader.setLocation(getClass().getResource("pages/main.fxml"));

        //injector.getAllBindings().forEach((k, v) -> System.out.println("key: " + k + ", v: " + v));

        Scene scene = new Scene(fxmlLoader.load(getClass().getResourceAsStream("pages/main.fxml")), APP_DEFAULT_WIDTH, APP_DEFAULT_HEIGHT);
        //scene.setFill(Color.TRANSPARENT);

        //stage.initStyle(StageStyle.UNDECORATED);
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(true);
        stage.setAlwaysOnTop(true);

        stage.setScene(scene);
        stage.show();
    }

    public void addTrayIcon(){
        // https://jinseongsoft.tistory.com/166
    }

    public static void main(String[] args) {
        launch();
    }
}