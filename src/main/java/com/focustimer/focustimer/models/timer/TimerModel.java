package com.focustimer.focustimer.models.timer;

import com.focustimer.focustimer.models.TemplateContainer;
import com.focustimer.focustimer.models.TemplateDataManager;
import com.focustimer.focustimer.models.TemplateObserver;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

@Getter
@Setter
public class TimerModel implements TemplateObserver {
    private final List<TimerModelObserver> stateOserverList = new Vector<>();
    private final List<TimerModelObserver> timeOserverList = new Vector<>();

    private TimerState state;
    private String goalStr;
    private double maxTime;
    private double startTime;
    private double curTime;

    @Override
    public void onTemplateNumChanged() {
        this.setState(TimerState.READY);
        TemplateDataManager dataManager = TemplateContainer.INSTANCE.getTemplateDataManager();
        this.goalStr = dataManager.readData("timer.goal");
        this.maxTime = Double.parseDouble(dataManager.readData("timer.maxTime"));
        this.startTime = Double.parseDouble(dataManager.readData("timer.startTime"));
        this.curTime = startTime;
    }

    public void loadData(int templateNum){
        // TODO refactor to MEMENTO
    }

    public void registerStateObservers(TimerModelObserver ...observers){
        this.stateOserverList.addAll(List.of(observers));
    }

    public void removeStateObservers(TimerModelObserver ...observers){
        this.stateOserverList.removeAll(List.of(observers));
    }

    public void notifyStateObservers(){
        for(TimerModelObserver observer : this.stateOserverList){
            observer.onTimerStateChanged();
        }
    }

    public void registerTimeObserver(TimerModelObserver ...observers){
        this.timeOserverList.addAll(List.of(observers));
    }

    public void removeTimeObserver(TimerModelObserver ...observers){
        this.timeOserverList.removeAll(List.of(observers));
    }

    public void notifyTimeObservers(){
        for(TimerModelObserver observer : this.timeOserverList){
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

    public void start(){
        setState(TimerState.RUNNING);
        // FIXME : TEMP
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setCurTime(getCurTime() - 0.33);
            }
        }, 1000, 33);
    };

    public void stop(){
        setState(TimerState.STOP);
    };

    public void pause(){
        setState(TimerState.PAUSE);
    };


}
