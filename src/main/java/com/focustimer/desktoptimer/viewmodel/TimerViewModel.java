package com.focustimer.desktoptimer.viewmodel;

import com.focustimer.desktoptimer.common.Inject;
import com.focustimer.desktoptimer.common.store.PersistingProperty;
import com.focustimer.desktoptimer.model.TimerModel;
import com.focustimer.desktoptimer.service.TimerService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TimerViewModel {
    private final TimerModel timerModel;
    private final TimerService timerService;

    public final LongProperty maxTime = new SimpleLongProperty();
    public final LongProperty startTime = new SimpleLongProperty();
    public final LongProperty curTime = new SimpleLongProperty();
    public final StringProperty timerName = new SimpleStringProperty();

    public final LongProperty mainMaxTime =
            PersistingProperty.create(LongProperty.class, "mainTimerMaxTime", "3600");
    public final LongProperty mainStartTime =
            PersistingProperty.create(LongProperty.class, "mainTimerStartTime", "2400");
    public final LongProperty mainCurTime = new SimpleLongProperty(mainStartTime.get());
    public final StringProperty mainTimerName = new SimpleStringProperty("Focus");

    public final LongProperty pomodoroMaxTime =
            PersistingProperty.create(LongProperty.class, "pomodoroTimerMaxTime", "3600");
    public final LongProperty pomodoroStartTime =
            PersistingProperty.create(LongProperty.class, "pomodoroTimerStartTime", "300");
    public final LongProperty pomodoroCurTime = new SimpleLongProperty(pomodoroStartTime.get());
    public final StringProperty pomodoroTimerName = new SimpleStringProperty("Rest");

    public final BooleanProperty isPomodoroMode = new SimpleBooleanProperty(false);
    public final BooleanProperty usingPomodoro =
            PersistingProperty.create(BooleanProperty.class, "usingPomodoro", "true");
    public final BooleanProperty isTimerRunning = new SimpleBooleanProperty(false);

    @Inject
    public TimerViewModel(TimerModel timerModel, TimerService timerService) {
        this.timerModel = timerModel;
        this.timerService = timerService;

        maxTime.bindBidirectional(timerModel.maxTime);
        startTime.bindBidirectional(timerModel.startTime);
        curTime.bindBidirectional(timerModel.curTime);
        timerName.bindBidirectional(timerModel.timerName);

        setMainTimer();
        isTimerRunning.bind(timerService.runningProperty());
        timerService.setOnSucceeded(workerStateEvent -> {
            if (usingPomodoro.get()) {
                if (isPomodoroMode.get()) {
                    unsetPomodoroTimer();
                    setMainTimer();
                } else {
                    unsetMainTimer();
                    setPomodoroTimer();
                }
            }
        });
    }

    public void startTimer() {
        timerService.restart();
    }

    public void pauseTimer() {
        timerService.cancel();
    }

    public void stopTimer() {
        timerService.cancel();
        curTime.set(startTime.get());
    }

    public void resetTimer() {
        if (!isTimerRunning.get()) {
            curTime.set(startTime.get());
        }
    }

    public void setMainTimer() {
        isPomodoroMode.set(false);
        mainCurTime.set(mainStartTime.get());
        maxTime.bindBidirectional(this.mainMaxTime);
        startTime.bindBidirectional(this.mainStartTime);
        curTime.bindBidirectional(this.mainCurTime);
        timerName.bindBidirectional(this.mainTimerName);
    }

    public void unsetMainTimer() {
        isPomodoroMode.set(true);
        maxTime.unbindBidirectional(this.mainMaxTime);
        startTime.unbindBidirectional(this.mainStartTime);
        curTime.unbindBidirectional(this.mainCurTime);
        timerName.unbindBidirectional(this.mainTimerName);
    }

    public void setPomodoroTimer() {
        isPomodoroMode.set(true);
        pomodoroCurTime.set(pomodoroStartTime.get());
        maxTime.bindBidirectional(this.pomodoroMaxTime);
        startTime.bindBidirectional(this.pomodoroStartTime);
        curTime.bindBidirectional(this.pomodoroCurTime);
        timerName.bindBidirectional(this.pomodoroTimerName);
    }

    public void unsetPomodoroTimer() {
        isPomodoroMode.set(false);
        maxTime.unbindBidirectional(this.pomodoroMaxTime);
        startTime.unbindBidirectional(this.pomodoroStartTime);
        curTime.unbindBidirectional(this.pomodoroCurTime);
        timerName.unbindBidirectional(this.pomodoroTimerName);
    }
}
