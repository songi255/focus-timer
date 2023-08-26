package com.focustimer.focustimer.model.timer;

import com.focustimer.focustimer.config.autoscan.Bean;
import com.focustimer.focustimer.config.store.AfterDataInject;
import com.focustimer.focustimer.config.store.SaveWithTemplate;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Bean
@Getter
@Setter
public class TimerModel {
    private final List<TimerObserver> stateObserverList = new LinkedList<>();
    private final List<TimerObserver> timeObserverList = new LinkedList<>();
    private final List<TimerObserver> modeObserverList = new LinkedList<>();

    private TimerState state;
    @SaveWithTemplate("Study") private String goalStr;
    @SaveWithTemplate("3600") private double maxTime;
    @SaveWithTemplate("2400") private double startTime;
    private double curTime;

    private boolean isPomoMode = false;
    @SaveWithTemplate("3600") private double pomoMaxTime;
    @SaveWithTemplate("300") private double pomoStartTime;

    private final TimerService timerService = new TimerService(this);

    @AfterDataInject
    public void afterLoadData(){
        setState(TimerState.READY);
        setCurTime(startTime);
        setPomoMode(false);
    }

    public void startTimer(){
        timerService.startTimer();
    }

    public void stopTimer(){
        timerService.stopTimer();
    }

    public void pauseTimer(){
        timerService.pauseTimer();
    }

    public void registerStateObservers(TimerObserver...observers){
        this.stateObserverList.addAll(List.of(observers));
    }

    public void removeStateObservers(TimerObserver...observers){
        this.stateObserverList.removeAll(List.of(observers));
    }

    private void notifyStateObservers(){
        for(TimerObserver observer : this.stateObserverList){
            observer.onTimerStateChanged();
        }
    }

    public void registerTimeObserver(TimerObserver...observers){
        this.timeObserverList.addAll(List.of(observers));
    }

    public void removeTimeObserver(TimerObserver...observers){
        this.timeObserverList.removeAll(List.of(observers));
    }

    private void notifyTimeObservers(){
        for(TimerObserver observer : this.timeObserverList){
            observer.onTimerTimeChanged();
        }
    }

    public void registerModeObserver(TimerObserver...observers){
        this.modeObserverList.addAll(List.of(observers));
    }

    public void removeModeObserver(TimerObserver...observers){
        this.modeObserverList.removeAll(List.of(observers));
    }

    private void notifyModeObservers(){
        for(TimerObserver observer : this.modeObserverList){
            observer.onTimerModeChanged();
        }
    }


    public void setCurTime(double time){
        if (this.curTime == time) return;
        this.curTime = time;
        notifyTimeObservers();
    }

    public void setState(TimerState state) {
        if (this.state == state) return;
        this.state = state;
        notifyStateObservers();
    }

    public void setPomoMode(boolean isPomoMode) {
        if (this.isPomoMode == isPomoMode) return;
        this.isPomoMode = isPomoMode;
        notifyModeObservers();
    }
}
