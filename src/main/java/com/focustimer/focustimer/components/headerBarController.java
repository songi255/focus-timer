package com.focustimer.focustimer.components;

import com.focustimer.focustimer.model.template.TemplateModel;
import com.focustimer.focustimer.model.template.TemplateObserver;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class headerBarController implements Initializable, TemplateObserver {
    @FXML Text templateText;

    private final TemplateModel templateModel;

    @Inject
    public headerBarController(TemplateModel templateModel) {
        this.templateModel = templateModel;
        templateModel.registerObservers(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // temp
        templateText.setText("template-1");
    }

    @Override
    public void onTemplateNumChanged(int templateNum) {

    }
}
