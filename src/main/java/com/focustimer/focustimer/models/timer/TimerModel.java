package com.focustimer.focustimer.models.timer;

import com.focustimer.focustimer.models.TemplateObserver;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Vector;

@Getter
@Setter
public class TimerModel implements TemplateObserver {
    private TimerState state;
    private String goalStr;
    private double maxTime;
    private double timeSet;
    private double curTime;
    private final List<TimerModelObserver> observerList = new Vector<>();

    @Override
    public void onTemplateNumChanged() {
        this.setState(TimerState.READY);

    }

    public void initialize(int templateNum){

    }

    public void registerObserver(TimerModelObserver observer){
        this.observerList.add(observer);
    }

    public void removeObserver(TimerModelObserver observer){
        this.observerList.remove(observer);
    }

    public void notifyObservers(){

    }

    public void setState(TimerState state) {
        this.state = state;
    }

    public void start(){
        setState(TimerState.RUNNING);
    };

    public void stop(){
        setState(TimerState.STOP);
    };

    public void pause(){
        setState(TimerState.PAUSE);
    };


}
