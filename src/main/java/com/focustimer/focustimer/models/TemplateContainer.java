/**
 * singleton class holing models and weave each other.
 *
 */

package com.focustimer.focustimer.models;

import com.focustimer.focustimer.models.timer.TimerModel;
import lombok.Getter;

@Getter
public enum TemplateContainer {
    INSTANCE;

    private int templateNum;

    public void setTemplateNum(int templateNum){
        this.templateNum = templateNum;
    }

    private final TimerModel timerModel = new TimerModel();

}
