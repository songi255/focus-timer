/**
 * singleton class holing models and weave each other.
 *
 */

package com.focustimer.focustimer.models;

import com.focustimer.focustimer.models.timer.TimerModel;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

@Getter
public enum TemplateContainer {
    INSTANCE;

    private int templateNum = -1;
    private final List<TemplateObserver> templateObserverList = new LinkedList<>();

    public void registerObserver(TemplateObserver observer){
        this.templateObserverList.add(observer);
    }

    public void removeObserver(TemplateObserver observer){
        this.templateObserverList.remove(observer);
    }

    public void notifyTemplateNumChanged(){
        for(TemplateObserver observer : this.templateObserverList){
            observer.onTemplateNumChanged();
        }
    }

    public void setTemplateNum(int templateNum){
        this.templateNum = templateNum;
        notifyTemplateNumChanged();
    }

    private final TimerModel timerModel = new TimerModel();
    // and more Models...
}
