package com.focustimer.focustimer.models.timer;

public interface TimerModelObserver {
    public default void onTimerStateChanged(){};
    public default void onTimerTimeChanged(){};
}
