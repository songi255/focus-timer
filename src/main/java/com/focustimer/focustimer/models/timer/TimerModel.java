package com.focustimer.focustimer.models.timer;

import com.focustimer.focustimer.models.ContainerObserver;
import com.focustimer.focustimer.models.DataManager;
import com.focustimer.focustimer.models.template.TemplateObserver;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Vector;

@Getter
@Setter
public class TimerModel implements ContainerObserver, TemplateObserver {
    private DataManager dataManager;

    private final List<TimerObserver> stateOserverList = new Vector<>();
    private final List<TimerObserver> timeOserverList = new Vector<>();

    private TimerState state;
    private String goalStr;
    private double maxTime;
    private double startTime;
    private double curTime;
    private int templateNum;

    // timer service is dependent on timerModel... so did not inject at assembler...
    private final TimerService timerService = new TimerService(this);

    @Override
    public void onContainerInitialized() {

    }

    @Override
    public void onTemplateNumChanged(int templateNum) {
        loadData(templateNum);
    }

    private void loadData(int templateNum){
        this.templateNum = templateNum;
        this.goalStr = dataManager.getData(DataManager.generateKey(templateNum, "timer.goalStr"));
        this.maxTime = Double.parseDouble(dataManager.getData(DataManager.generateKey(templateNum, "timer.maxTime")));
        this.startTime = Double.parseDouble(dataManager.getData(DataManager.generateKey(templateNum, "timer.startTime")));
        setState(TimerState.READY);
        setCurTime(startTime);
    }

    public void start(){
        if (state == TimerState.READY || state == TimerState.PAUSE || state == TimerState.STOP){
            timerService.restart();
            setState(TimerState.RUNNING);
        }
    };

    public void stop(){
        if (state == TimerState.RUNNING || state == TimerState.PAUSE || state == TimerState.FINISH){
            timerService.cancel();
            setCurTime(startTime);
            setState(TimerState.STOP);
        }
    };

    public void pause(){
        if(state == TimerState.RUNNING){
            timerService.cancel();
            setState(TimerState.PAUSE);
        }
    };

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

    public void setGoalStr(String goalStr) {
        this.goalStr = goalStr;
        dataManager.setData(DataManager.generateKey(templateNum, "timer.goalStr"), goalStr);
    }

    public void setMaxTime(double maxTime) {
        this.maxTime = maxTime;
        dataManager.setData(DataManager.generateKey(templateNum, "timer.maxTime"), String.valueOf(this.maxTime));
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
        dataManager.setData(DataManager.generateKey(templateNum, "timer.startTime"), String.valueOf(this.startTime));
    }
}
