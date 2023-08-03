package com.focustimer.focustimer.model.timer;

public interface TimerObserver {
    public default void onTimerStateChanged(){};
    public default void onTimerTimeChanged(){};
}
