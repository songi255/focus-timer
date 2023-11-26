package com.focustimer.desktoptimer.service;

import com.focustimer.desktoptimer.common.store.PersistingProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
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

//
//    public void setOverlayXGapByX(double stageX){
//        setOverlayXGap(ScreenUtil.getCurScreen(stage).getBounds().getWidth() - stageX - stage.getWidth());
//    }
//
//    public void setOverlayYGapByY(double stageY){
//        setOverlayYGap(ScreenUtil.getCurScreen(stage).getBounds().getHeight() - stageY - stage.getHeight());
//    }
//
//    public void bindStage(){
//        stage.widthProperty().addListener(getCallback(this::setOriginalWidth, this::setOverlayWidth));
//        stage.heightProperty().addListener(getCallback(this::setOriginalHeight, this::setOverlayHeight));
//        stage.xProperty().addListener(getCallback(this::setOriginalX, this::setOverlayXGapByX));
//        stage.yProperty().addListener(getCallback(this::setOriginalY, this::setOverlayYGapByY));
//    }
//
//    private ChangeListener<Number> getCallback(Consumer<Double> originalSetter, Consumer<Double> overlaySetter){
//        return new ChangeListener<>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
//                if (isServiceRunning()) return;
//                if (isOverlayState()) {
//                    overlaySetter.accept(t1.doubleValue());
//                } else {
//                    originalSetter.accept(t1.doubleValue());
//                }
//            }
//        };
//    }
}
