package com.focustimer.desktoptimer.util;

import javafx.event.Event;
import javafx.event.EventType;

public class StageShownEvent extends Event {
    public static final EventType<StageShownEvent> STAGE_SHOWN = new EventType<>(Event.ANY, "STAGE_SHOWN");

    public StageShownEvent() {
        super(STAGE_SHOWN);
    }
}
