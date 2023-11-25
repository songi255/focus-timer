package com.focustimer.desktoptimer.component.timerdisk;

import com.focustimer.desktoptimer.common.store.PersistingProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

public class TimerDiskSetting {
    public final StringProperty mainTimerColor =
            PersistingProperty.create(StringProperty.class, "mainTimerColor", "D04E4E");
    public final StringProperty pomodoroTimerColor =
            PersistingProperty.create(StringProperty.class, "pomodoroTimerColor", "4EA1D0");
    public final StringProperty mainScaleColor =
            PersistingProperty.create(StringProperty.class, "mainScaleColor", "black");
    public final StringProperty subScaleColor =
            PersistingProperty.create(StringProperty.class, "subScaleColor", "black");
    public final StringProperty textColor =
            PersistingProperty.create(StringProperty.class, "textColor", "black");

    public final IntegerProperty mainScaleCount = new SimpleIntegerProperty(12);
    public final IntegerProperty subScaleCount = new SimpleIntegerProperty(12 * 5);
    public final DoubleProperty numberSizeRatio = new SimpleDoubleProperty(0.15);
    public final DoubleProperty scaleSizeRatio = new SimpleDoubleProperty(0.1);
}
