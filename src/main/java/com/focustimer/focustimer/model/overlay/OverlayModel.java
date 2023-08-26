package com.focustimer.focustimer.model.overlay;

import com.focustimer.focustimer.config.autoscan.Bean;
import com.focustimer.focustimer.config.store.Save;
import com.focustimer.focustimer.utils.ScreenUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
@Bean
@Getter
@Setter
public class OverlayModel {
    public final static long INTERVAL = (long) (1000.0 / 60.0); // 60 fps
    public final static double RATIO = 0.075;

    @Save("0.2") private double overlayOpacity;
    @Save("140") private double overlayWidth;
    @Save("140") private double overlayHeight;
    @Save("100") private double overlayXGap;
    @Save("100") private double overlayYGap;

    @Save("360") private double originalWidth;
    @Save("480") private double originalHeight;
    @Save("0") private double originalX;
    @Save("0") private double originalY;

    private boolean isOverlayState = false;
    private boolean isServiceRunning = false;

    private Stage stage;
    private final OverlayService overlayService = new OverlayService(this);

    public void overlay(){
        if (stage.isFullScreen()) return;
        log.info("overlay");
        overlayService.overlay();
    }

    public void unOverlay(){
        if (stage.isFullScreen()) return;
        log.info("unOverlay");
        overlayService.unOverlay();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        setOriginalWidth(stage.getWidth());
        setOriginalHeight(stage.getHeight());
        setOriginalX(stage.getX());
        setOriginalY(stage.getY());
        bindStage();
    }

    public void setOverlayXGapByX(double stageX){
        setOverlayXGap(ScreenUtil.getCurScreen(stage).getBounds().getWidth() - stageX - stage.getWidth());
    }

    public void setOverlayYGapByY(double stageY){
        setOverlayYGap(ScreenUtil.getCurScreen(stage).getBounds().getHeight() - stageY - stage.getHeight());
    }

    public void bindStage(){
        stage.widthProperty().addListener(getCallback(this::setOriginalWidth, this::setOverlayWidth));
        stage.heightProperty().addListener(getCallback(this::setOriginalHeight, this::setOverlayHeight));
        stage.xProperty().addListener(getCallback(this::setOriginalX, this::setOverlayXGapByX));
        stage.yProperty().addListener(getCallback(this::setOriginalY, this::setOverlayYGapByY));
    }

    private ChangeListener<Number> getCallback(Consumer<Double> originalSetter, Consumer<Double> overlaySetter){
        return new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (isServiceRunning()) return;
                if (isOverlayState()) {
                    overlaySetter.accept(t1.doubleValue());
                } else {
                    originalSetter.accept(t1.doubleValue());
                }
            }
        };
    }
}
