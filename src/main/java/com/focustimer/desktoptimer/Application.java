package com.focustimer.desktoptimer;

import com.focustimer.desktoptimer.common.DIContext;
import com.focustimer.desktoptimer.common.DIContext.Scope;
import com.focustimer.desktoptimer.config.ApplicationSetting;
import com.focustimer.desktoptimer.util.StageShownEvent;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.stage.StageStyle;

public class Application extends javafx.application.Application {

    @Override
    public void init() throws Exception {
        System.setProperty("prism.lcdtext", "false");
        Font.loadFont(getClass().getResourceAsStream("fonts/Inter-Regular.ttf"), 11);

        super.init();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(clazz -> DIContext.INSTANCE.getInstance(clazz, Scope.TEMPORAL));
        fxmlLoader.setLocation(getClass().getResource("page/main/main.fxml"));

        Parent root = fxmlLoader.load(getClass().getResourceAsStream("page/main/main.fxml"));
        double padding = 20;
        Scene scene = new Scene(root,
                ApplicationSetting.defaultAppWidth.get() + padding * 2,
                ApplicationSetting.defaultAppHeight.get() + padding * 2);
        scene.setFill(Color.TRANSPARENT);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);

        stage.setTitle("Focus Timer");
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("icons/app_icon.png")));
        stage.setScene(scene);
        stage.show();

        root.fireEvent(new StageShownEvent());
    }

    public static void main(String[] args) {
        launch();
    }
}