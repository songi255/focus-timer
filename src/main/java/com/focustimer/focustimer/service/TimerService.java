package com.focustimer.focustimer.service;

import com.focustimer.focustimer.config.autoscan.ServiceBean;
import com.focustimer.focustimer.model.timer.TimerModel;
import com.focustimer.focustimer.model.timer.TimerState;
import com.google.inject.Inject;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.scene.control.Label;

@ServiceBean
public class TimerService extends Service<Void> {
    private final TimerModel timerModel;

    @Inject
    public TimerService(TimerModel timerModel) {
        this.timerModel = timerModel;
        this.setOnSucceeded(e -> {
            System.out.println("setOnSucceeded called in " + Thread.currentThread());
            timerModel.setCurTime(0);
            timerModel.setState(TimerState.FINISH);
        });
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                long startTime = System.currentTimeMillis();
                double startSec = timerModel.getCurTime();
                double passedTime = 0;

                int count = 0;

                while(passedTime < startSec){
                    //temp
                    Thread.sleep(1000 / 60); // 60 fps

                    passedTime = (System.currentTimeMillis() - startTime) / 1000.0 * 100;
                    timerModel.setCurTime(startSec - passedTime);
                }

                System.out.println("task Called in " + Thread.currentThread());

                return null;
            }
        };
    }

    public void startTimer(){
        TimerState state = timerModel.getState();
        if (state == TimerState.READY || state == TimerState.PAUSE || state == TimerState.STOP){
            restart();
            timerModel.setState(TimerState.RUNNING);
        }
    };

    public void stopTimer(){
        TimerState state = timerModel.getState();
        if (state == TimerState.RUNNING || state == TimerState.PAUSE || state == TimerState.FINISH){
            cancel();
            timerModel.setCurTime(timerModel.getStartTime());
            timerModel.setState(TimerState.STOP);
        }
    };

    public void pauseTimer(){
        TimerState state = timerModel.getState();
        if(state == TimerState.RUNNING){
            cancel();
            timerModel.setState(TimerState.PAUSE);
        }
    };
}
