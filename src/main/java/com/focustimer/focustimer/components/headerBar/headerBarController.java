package com.focustimer.focustimer.components.headerBar;

import com.focustimer.focustimer.config.store.TemplateModel;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

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
        // temp
        templateText.setText("template-1");
    }
}
