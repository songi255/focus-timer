package com.focustimer.focustimer.model.timer;

import com.google.inject.Inject;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.scene.control.Label;

@com.focustimer.focustimer.config.autoscan.Service
public class TimerService extends Service<Void> {
    private final TimerModel timerModel;

    @Inject
    public TimerService(TimerModel timerModel) {
        this.timerModel = timerModel;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                long startTime = System.currentTimeMillis();
                double startSec = timerModel.getCurTime();
                double passedTime = 0;

                while(passedTime < startSec){
                    passedTime = (System.currentTimeMillis() - startTime) / 1000.0 * 100;
                    timerModel.setCurTime(startSec - passedTime);

                    new Label().fireEvent(new Event(Event.ANY));

                    //temp
                    Thread.sleep(1000 / 60); // 60 fps
                }

                timerModel.setCurTime(0);
                timerModel.setState(TimerState.FINISH);
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
