package com.focustimer.focustimer.models.timer;

public interface TimerObserver {
    public default void onTimerStateChanged(){};
    public default void onTimerTimeChanged(){};
}
