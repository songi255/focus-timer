package com.focustimer.focustimer.model.timer;

import com.focustimer.focustimer.config.autoscan.Bean;
import com.focustimer.focustimer.config.store.DataInjector;
import com.focustimer.focustimer.config.store.SaveWithTemplate;
import com.google.inject.Inject;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Bean
@Getter
@Setter
public class TimerModel {
    private final DataInjector dataInjector;

    private final List<TimerObserver> stateOserverList = new LinkedList<>();
    private final List<TimerObserver> timeOserverList = new LinkedList<>();

    private TimerState state;
    @SaveWithTemplate("goal") private String goalStr;
    @SaveWithTemplate("3600") private double maxTime;
    @SaveWithTemplate("2400") private double startTime;
    private double curTime;

    @Inject
    public TimerModel(DataInjector dataInjector) {
        this.dataInjector = dataInjector;
        loadData();
    }

    private void loadData(){
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
