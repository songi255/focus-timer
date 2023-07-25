package com.focustimer.focustimer.models.timer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TimerService extends Service<Void> {
    private final TimerModel timerModel;

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

                    //temp
                    Thread.sleep(1000 / 144);
                }

                timerModel.setCurTime(0);
                return null;
            }
        };
    }
}
