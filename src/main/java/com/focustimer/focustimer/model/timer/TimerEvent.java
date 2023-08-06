package com.focustimer.focustimer.model.timer;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

@Getter
public class TimerEvent extends Event {
    public static final EventType<TimerEvent> TIMER_TIME_CHANGE = new EventType<>(Event.ANY, "TIMER_TIME_CHANGE");
    public static final EventType<TimerEvent> TIMER_STATE_CHANGE = new EventType<>(Event.ANY, "TIMER_STATE_CHANGE");

    private double time;
    private TimerState state;

    public TimerEvent(double time) {
        super(TIMER_TIME_CHANGE);
        this.time = time;
    }

    public TimerEvent(TimerState state) {
        super(TIMER_STATE_CHANGE);
        this.state = state;
    }
}
