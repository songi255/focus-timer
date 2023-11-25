package com.focustimer.desktoptimer.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TimerModel {
    public final LongProperty maxTime = new SimpleLongProperty();
    public final LongProperty startTime = new SimpleLongProperty();
    public final LongProperty curTime = new SimpleLongProperty();
    public final StringProperty timerName = new SimpleStringProperty();
}
