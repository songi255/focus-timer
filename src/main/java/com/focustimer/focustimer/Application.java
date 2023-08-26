package com.focustimer.focustimer;

import com.focustimer.focustimer.config.AppModule;
import com.focustimer.focustimer.config.store.DataInjector;
import com.focustimer.focustimer.config.store.TemplateModel;
import com.focustimer.focustimer.utils.TrayNotification;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;

public class Application extends javafx.application.Application {
    final static int APP_DEFAULT_WIDTH = 360 + 40;
    final static int APP_DEFAULT_HEIGHT = 480 + 20;

    @Override
    public void init() throws Exception {
        System.setProperty("prism.lcdtext", "false");
        Font.loadFont(getClass().getResourceAsStream("fonts/Inter-Regular.ttf"), 11);

        super.init();
    }

    @Override
    public void stop() throws Exception {
        // clean resources(file, network,,,)
        super.stop();
        System.exit(0); // AWT
    }

    @Override
    public void start(Stage stage) throws IOException {
        Injector injector = Guice.createInjector(new AppModule());
        injector.getInstance(DataInjector.class).injectAll();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(injector::getInstance);
        fxmlLoader.setLocation(getClass().getResource("pages/main/main.fxml"));

        Scene scene = new Scene(fxmlLoader.load(getClass().getResourceAsStream("pages/main/main.fxml")), APP_DEFAULT_WIDTH, APP_DEFAULT_HEIGHT);
        scene.setFill(Color.TRANSPARENT);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(true);
        stage.setAlwaysOnTop(true);

        GridPane root = (GridPane) scene.getRoot();
        root.setPadding(new Insets(20));
        root.setEffect(new DropShadow(10, 2, 2, Color.valueOf("Gray")));

        stage.setTitle("Focus Timer");
        stage.setScene(scene);
        stage.show();
    }

    public void addTrayIcon(){

    }

    public static void main(String[] args) {
        launch();
    }
}