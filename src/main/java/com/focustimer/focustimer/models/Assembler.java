/**
 * perform DI for each models.
 *
 * @author Dave Shin
 */
package com.focustimer.focustimer.models;

import com.focustimer.focustimer.models.template.TemplateModel;
import com.focustimer.focustimer.models.timer.TimerModel;

public class Assembler {
    // DI
    public void assemble(){
        ModelContainer container = ModelContainer.CONTAINER;
        TemplateModel templateModel = new TemplateModel();
        TimerModel timerModel = new TimerModel();
        DataManager dataManager = new DataManager();

        container.setTimerModel(timerModel);
        container.setTemplateModel(templateModel);
        container.setDataManager(dataManager);

        templateModel.registerObservers(timerModel);

        timerModel.setDataManager(dataManager);


    }
}
