/**
 * get Dependencies with this singleton container.
 *
 */

package com.focustimer.focustimer.models;

import com.focustimer.focustimer.models.template.TemplateModel;
import com.focustimer.focustimer.models.timer.TimerModel;
import lombok.Getter;

@Getter
public enum ModelContainer {
    CONTAINER;
    private TemplateModel templateModel;
    private TimerModel timerModel;
    private DataManager dataManager;

    public void setTemplateModel(TemplateModel templateModel) {
        this.templateModel = templateModel;
    }

    public void setTimerModel(TimerModel timerModel) {
        this.timerModel = timerModel;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }
}
