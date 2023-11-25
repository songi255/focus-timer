package com.focustimer.desktoptimer.config;

import com.focustimer.desktoptimer.common.store.PersistingProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;

public class ApplicationSetting {
    public static final IntegerProperty defaultAppWidth =
            PersistingProperty.create(IntegerProperty.class, "defaultAppWidth", "360");
    public static final IntegerProperty defaultAppHeight =
            PersistingProperty.create(IntegerProperty.class, "defaultAppHeight", "480");
    public static final BooleanProperty alarmSound =
            PersistingProperty.create(BooleanProperty.class, "alarmSound", "true");

}
