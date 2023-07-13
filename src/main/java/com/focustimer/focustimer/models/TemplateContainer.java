/**
 * singleton class holing models and weave each other.
 *
 */

package com.focustimer.focustimer.models;

import com.focustimer.focustimer.models.timer.TimerModel;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
public enum TemplateContainer {
    INSTANCE;

    private int templateNum;
    private final List<TemplateObserver> templateObserverList = new LinkedList<>();
    private TemplateDataManager templateDataManager;
    private TimerModel timerModel;
    // and more Models...

    // setters
    public void setTemplateDataManager(TemplateDataManager templateDataManager) {
        this.templateDataManager = templateDataManager;
    }

    public void setTimerModel(TimerModel timerModel) {
        this.timerModel = timerModel;
    }

    public void setTemplateNum(int templateNum){
        this.templateNum = templateNum;
        notifyTemplateNumChanged();
    }

    // observer
    public void registerObservers(TemplateObserver ...observers){
        this.templateObserverList.addAll(List.of(observers));
    }

    public void removeObservers(TemplateObserver ...observers){
        this.templateObserverList.removeAll(List.of(observers));
    }

    public void notifyTemplateNumChanged(){
        for(TemplateObserver observer : this.templateObserverList){
            observer.onTemplateNumChanged();
        }
    }
}
