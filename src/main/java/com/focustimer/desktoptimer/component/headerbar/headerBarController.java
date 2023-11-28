package com.focustimer.desktoptimer.component.headerbar;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class headerBarController implements Initializable {
    @FXML Text title;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setText("Focus Timer");
    }

    @FXML public void btnCloseHandler(){
        Platform.exit();
    }

    @FXML public void btnMinimizeHandler(){
        Stage stage = (Stage) title.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML public void btnFullScreenHandler(){
        Stage stage = (Stage) title.getScene().getWindow();
        stage.setFullScreen(!stage.isFullScreen());
        stage.setAlwaysOnTop(false);
        stage.setAlwaysOnTop(true);
    }
}
