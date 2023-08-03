/**
 * manage template number state
 *
 */

package com.focustimer.focustimer.model.template;

import com.focustimer.focustimer.config.autoscan.Bean;
import com.focustimer.focustimer.config.DataManager;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Bean
@Getter
@Setter
public class TemplateModel {
    private int templateNum;
    private String templateName;
    private final List<TemplateObserver> templateObserverList = new LinkedList<>();
    private DataManager dataManager;
    // and more Models...

    public void setTemplateNum(int templateNum){
        this.templateNum = templateNum;
        notifyTemplateNumChanged();
    }

    public void createTemplate(){

    }

    public void deleteTemplate(int templateNum){

    }

    // observer
    public void registerObservers(TemplateObserver ...observers){
        templateObserverList.addAll(List.of(observers));
    }

    public void removeObservers(TemplateObserver ...observers){
        templateObserverList.removeAll(List.of(observers));
    }

    private void notifyTemplateNumChanged(){
        templateObserverList.forEach(observer -> observer.onTemplateNumChanged(this.templateNum));
    }
}
