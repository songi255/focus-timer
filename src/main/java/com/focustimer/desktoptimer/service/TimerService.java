package com.focustimer.desktoptimer.service;

import com.focustimer.desktoptimer.common.Inject;
import com.focustimer.desktoptimer.model.TimerModel;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class TimerService extends Service<Void> {
    private static final long INTERVAL = (long) (1000.0 / 30);

    private final TimerModel timerModel;

    @Inject
    public TimerService(TimerModel timerModel) {
        this.timerModel = timerModel;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                long startTime = System.currentTimeMillis();
                long startSec = timerModel.curTime.longValue();
                long passedTime = 0;

                while (passedTime < startSec) {
                    // test. erase 1000 when deploy
                    passedTime = (System.currentTimeMillis() - startTime) / 1000 * 1000;
                    timerModel.curTime.set(startSec - passedTime);

                    Thread.sleep(INTERVAL);
                }

                return null;
            }
        };
    }
}