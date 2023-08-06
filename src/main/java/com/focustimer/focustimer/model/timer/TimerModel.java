package com.focustimer.focustimer.model.timer;

import com.focustimer.focustimer.config.autoscan.Bean;
import com.focustimer.focustimer.config.store.DataInjector;
import com.focustimer.focustimer.config.store.DataManager;
import com.focustimer.focustimer.config.store.Save;
import com.focustimer.focustimer.config.store.SaveWithTemplate;
import com.focustimer.focustimer.model.template.TemplateObserver;
import com.google.inject.Inject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Vector;

@Bean
@Getter
@Setter
public class TimerModel implements TemplateObserver {
    private final DataInjector dataInjector;

    private final List<TimerObserver> stateOserverList = new Vector<>();
    private final List<TimerObserver> timeOserverList = new Vector<>();

    private TimerState state;
    @SaveWithTemplate("goal") private String goalStr;
    @SaveWithTemplate("3600") private double maxTime;
    @SaveWithTemplate("2400") private double startTime;
    private double curTime;
    private int templateNum;

    @Inject
    public TimerModel(DataInjector dataInjector) {
        this.dataInjector = dataInjector;
        // temp
        onTemplateNumChanged(1);
    }

    @Override
    public void onTemplateNumChanged(int templateNum) {
        loadData(templateNum);
    }

    private void loadData(int templateNum){
        this.templateNum = templateNum;
        dataInjector.inject(this);
        setState(TimerState.READY);
        setCurTime(startTime);
    }

    public void registerStateObservers(TimerObserver...observers){
        this.stateOserverList.addAll(List.of(observers));
    }

    public void removeStateObservers(TimerObserver...observers){
        this.stateOserverList.removeAll(List.of(observers));
    }

    private void notifyStateObservers(){
        for(TimerObserver observer : this.stateOserverList){
            observer.onTimerStateChanged();
        }
    }

    public void registerTimeObserver(TimerObserver...observers){
        this.timeOserverList.addAll(List.of(observers));
    }

    public void removeTimeObserver(TimerObserver...observers){
        this.timeOserverList.removeAll(List.of(observers));
    }

    private void notifyTimeObservers(){
        for(TimerObserver observer : this.timeOserverList){
            observer.onTimerTimeChanged();
        }
    }

    public void setCurTime(double time){
        this.curTime = time;
        notifyTimeObservers();
    }

    public void setState(TimerState state) {
        this.state = state;
        notifyStateObservers();
    }
}
