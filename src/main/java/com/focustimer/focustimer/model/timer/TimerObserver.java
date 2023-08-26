package com.focustimer.focustimer.model.timer;

public interface TimerObserver {
    default void onTimerStateChanged(){}
    default void onTimerTimeChanged(){}

    default void onTimerModeChanged(){}
}
