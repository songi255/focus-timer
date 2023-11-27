/**
 * Provides overlay animation
 * <p>
 * this service modify window's - opacity - size - position
 *
 * @author Dave Shin
 */

package com.focustimer.desktoptimer.service;

import com.focustimer.desktoptimer.common.Inject;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import static com.focustimer.desktoptimer.util.NewValueListener.listen;

@Slf4j
public class StageService extends AnimationTimer {
    private Stage stage;
    private final StageSetting setting;
    private final double screenWidth = Screen.getPrimary().getBounds().getWidth();
    private final double screenHeight = Screen.getPrimary().getBounds().getHeight();

    public final BooleanProperty isOverlayMode = new SimpleBooleanProperty(false);
    public final BooleanProperty isOverlayRunning = new SimpleBooleanProperty(false);

    // from-to Array for { Opacity, Width, Height, X, Y }
    private final int ITEM_COUNT = 5;
    private final double[] from = new double[ITEM_COUNT];
    private final double[] to = new double[ITEM_COUNT];

    private long lastUpdated = 0;

    @Inject
    public StageService(StageSetting setting) {
        this.setting = setting;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        setting.originalWidth.set(stage.getWidth());
        setting.originalHeight.set(stage.getHeight());
        setting.originalX.set(stage.getX());
        setting.originalY.set(stage.getY());
        bindStage(stage);
    }

    public void bindStage(Stage stage) {
        DoubleProperty overlayX = new SimpleDoubleProperty();
        DoubleProperty overlayY = new SimpleDoubleProperty();
        bindOnOverlayCondition(stage.xProperty(), setting.originalX, overlayX);
        bindOnOverlayCondition(stage.yProperty(), setting.originalY, overlayY);
        bindOnOverlayCondition(stage.widthProperty(), setting.originalWidth, setting.overlayWidth);
        bindOnOverlayCondition(stage.heightProperty(), setting.originalHeight, setting.overlayHeight);
        overlayX.addListener(listen(stageX -> {
            setting.overlayXGap.set(
                    Screen.getPrimary().getBounds().getWidth() - (double) stageX - setting.overlayWidth.get());
        }));
        overlayY.addListener(listen(stageY -> {
            setting.overlayYGap.set(
                    Screen.getPrimary().getBounds().getHeight() - (double) stageY - setting.overlayHeight.get());
        }));
    }

    private <T> void bindOnOverlayCondition(ObservableValue<T> source, Property<T> normal, Property<T> overlay) {
        source.addListener(listen(newValue -> {
            if (isOverlayRunning.get()) {
                return;
            }

            Property<T> target = isOverlayMode.get() ? overlay : normal;
            target.setValue(source.getValue());
        }));
    }

    public void overlay() {
        if (stage.isFullScreen()) {
            return;
        }

        if (isOverlayMode.get()) {
            return;
        }
        if (isOverlayRunning.get()) {
            stop();
        }

        isOverlayMode.set(true);
        setup();
        start();
    }

    public void unOverlay() {
        if (stage.isFullScreen()) {
            return;
        }

        if (!isOverlayMode.get()) {
            return;
        }
        if (isOverlayRunning.get()) {
            stop();
        }

        isOverlayMode.set(false);
        setup();
        flip();
        start();
    }

    public void flipOverlay() {
        if (isOverlayMode.get()) {
            unOverlay();
        } else {
            overlay();
        }
    }

    private void setup() {
        from[0] = setting.originalOpacity.get();
        from[1] = setting.originalWidth.get();
        from[2] = setting.originalHeight.get();
        from[3] = setting.originalX.get();
        from[4] = setting.originalY.get();

        to[0] = setting.overlayOpacity.get();
        to[1] = setting.overlayWidth.get();
        to[2] = setting.overlayHeight.get();
        to[3] = screenWidth - (to[1] + setting.overlayXGap.get());
        to[4] = screenHeight - (to[2] + setting.overlayYGap.get());
    }

    private void flip() {
        for (int i = 0; i < ITEM_COUNT; i++) {
            double temp = from[i];
            from[i] = to[i];
            to[i] = temp;
        }
    }

    @Override
    public void handle(long now) {
        if (now - lastUpdated < setting.FRAME_INTERVAL) {
            return;
        }
        lastUpdated = now;

        boolean allSame = true;
        for (int i = 0; i < ITEM_COUNT; i++) {
            from[i] = (from[i] + to[i] * setting.RATIO) / (setting.RATIO + 1);

            if ((to[i] - from[i]) > 1.0) {
                allSame = false;
            }
        }

        stage.setOpacity(from[0]);
        stage.setWidth(from[1]);
        stage.setHeight(from[2]);
        stage.setX(from[3]);
        stage.setY(from[4]);

        if (allSame) {
            stage.setOpacity(to[0]);
            stage.setWidth(to[1]);
            stage.setHeight(to[2]);
            stage.setX(to[3]);
            stage.setY(to[4]);
            stop();
        }
    }

    @Override
    public void start() {
        super.start();
        this.isOverlayRunning.set(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.isOverlayRunning.set(false);
    }
}
