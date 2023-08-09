/**
 * manage template number state
 *
 */

package com.focustimer.focustimer.config.store;

import com.focustimer.focustimer.config.autoscan.Bean;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Bean
@Getter
@Setter
public class TemplateModel {
    @Save("1") private int templateCount;
    @SaveWithTemplate("1") private int templateNum;
    @SaveWithTemplate("template") private String templateName;

    private final List<Procedure> listeners = new LinkedList<>();

    public void addTemplateNumListener(Procedure callback){
        listeners.add(callback);
    }

    public void setTemplateNum(int templateNum){
        if (this.templateNum == templateNum) return;
        this.templateNum = templateNum;

        for(Procedure callback : listeners) {
            callback.invoke();
        }
    }

    public void createTemplate(){

    }

    public void deleteTemplate(int templateNum){

    }
}