package com.focustimer.focustimer.components;

import com.focustimer.focustimer.models.ModelContainer;
import com.focustimer.focustimer.models.template.TemplateModel;
import com.focustimer.focustimer.models.template.TemplateObserver;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class headerBarController implements Initializable, TemplateObserver {
    private TemplateModel templateModel;

    @FXML Text templateText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.templateModel = ModelContainer.CONTAINER.getTemplateModel();
        templateModel.registerObservers(this);
        // temp
        templateText.setText("template-1");
    }

    @Override
    public void onTemplateNumChanged(int templateNum) {

    }
}
