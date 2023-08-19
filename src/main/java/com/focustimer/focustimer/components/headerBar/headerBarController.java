package com.focustimer.focustimer.components.headerBar;

import com.focustimer.focustimer.config.store.TemplateModel;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class headerBarController implements Initializable {
    @FXML Text templateText;

    private final TemplateModel templateModel;

    @Inject
    public headerBarController(TemplateModel templateModel) {
        this.templateModel = templateModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        templateText.setFont(new Font("Inter", 20));
        // temp
        templateText.setText("Template-1");
    }

    @FXML public void btnCloseHandler(){
        Platform.exit();
        System.exit(0);
    }

    @FXML public void btnMinimizeHandler(){
        Stage stage = (Stage) templateText.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML public void btnFullScreenHandler(){
        Stage stage = (Stage) templateText.getScene().getWindow();
        stage.setFullScreen(!stage.isFullScreen());
    }

    @FXML public void btnMenuHandler(){

    }
}
