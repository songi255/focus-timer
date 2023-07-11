package com.focustimer.focustimer.models.timer;

import com.focustimer.focustimer.models.TemplateContainer;
import com.focustimer.focustimer.models.TemplateObserver;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Properties;
import java.util.Vector;

@Getter
@Setter
public class TimerModel implements TemplateObserver {
    private final TemplateContainer container = TemplateContainer.INSTANCE;
    private final List<TimerModelObserver> stateOserverList = new Vector<>();
    private final List<TimerModelObserver> timeOserverList = new Vector<>();

    private TimerState state;
    private String goalStr;
    private double maxTime;
    private double timeSet;
    private double curTime;


    public TimerModel() {
        container.registerObserver(this);
    }

    @Override
    public void onTemplateNumChanged() {
        this.setState(TimerState.READY);

    }

    public void loadData(int templateNum){
        Properties properties = new Properties();
        // TODO refactor to MEMENTO
    }

    public void registerStateObserver(TimerModelObserver observer){
        this.stateOserverList.add(observer);
    }

    public void removeStateObserver(TimerModelObserver observer){
        this.stateOserverList.remove(observer);
    }

    public void notifyStateObservers(){
        for(TimerModelObserver observer : this.stateOserverList){
            observer.onTimerStateChanged();
        }
    }

    public void registerTimeObserver(TimerModelObserver observer){
        this.timeOserverList.add(observer);
    }

    public void removeTimeObserver(TimerModelObserver observer){
        this.timeOserverList.remove(observer);
    }

    public void notifyTimeObservers(){
        for(TimerModelObserver observer : this.timeOserverList){
            observer.onTimerTimeChanged();
        }
    }

    public void setTime(double time){
        this.curTime = time;
        notifyTimeObservers();
    }

    public void setState(TimerState state) {
        this.state = state;
        notifyStateObservers();
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
