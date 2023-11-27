package com.focustimer.desktoptimer.service;

import com.focustimer.desktoptimer.common.store.PersistingProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StageSetting {
    public final long FRAME_INTERVAL = (long) (1000_0000.0 / 30); // nanoTime base
    public final double RATIO = 0.075;

    public final DoubleProperty overlayOpacity =
            PersistingProperty.create(DoubleProperty.class, "overlayOpacity", "0.2");
    public final DoubleProperty overlayWidth =
            PersistingProperty.create(DoubleProperty.class, "overlayWidth", "140");
    public final DoubleProperty overlayHeight =
            PersistingProperty.create(DoubleProperty.class, "overlayHeight", "140");
    public final DoubleProperty overlayXGap =
            PersistingProperty.create(DoubleProperty.class, "overlayXGap", "100");
    public final DoubleProperty overlayYGap =
            PersistingProperty.create(DoubleProperty.class, "overlayYGap", "100");

    public final DoubleProperty originalOpacity =
            PersistingProperty.create(DoubleProperty.class, "originalOpacity", "1");
    public final DoubleProperty originalWidth =
            PersistingProperty.create(DoubleProperty.class, "originalWidth", "360");
    public final DoubleProperty originalHeight =
            PersistingProperty.create(DoubleProperty.class, "originalHeight", "480");
    public final DoubleProperty originalX = new SimpleDoubleProperty();
    public final DoubleProperty originalY = new SimpleDoubleProperty();
}
