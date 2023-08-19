/**
 * Provides overlay animation
 *
 * this service modify window's
 *  - opacity
 *  - size
 *  - position
 *
 * @author Dave Shin
 */

package com.focustimer.focustimer.model.overlay;

import com.focustimer.focustimer.config.autoscan.ServiceBean;
import com.focustimer.focustimer.utils.ScreenUtil;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ServiceBean
public class OverlayService extends Service<Void> {
    private final OverlayModel overlayModel;

    @Inject
    public OverlayService(OverlayModel overlayModel) {
        this.overlayModel = overlayModel;
        this.runningProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue) overlayModel.setServiceRunning(false);
        });
    }

    public void overlay(){
        if(overlayModel.isOverlayState()) return;
        if(overlayModel.isServiceRunning()) cancel();

        overlayModel.setServiceRunning(true);
        overlayModel.setOverlayState(true);
        restart();
    }

    public void unOverlay(){
        if (!overlayModel.isOverlayState()) return;
        if (overlayModel.isServiceRunning()) cancel();

        overlayModel.setServiceRunning(true);
        overlayModel.setOverlayState(false);
        restart();
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Stage stage = overlayModel.getStage();

                Screen curScreen = ScreenUtil.getCurScreen(stage);
                double screenWidth = curScreen.getBounds().getWidth();
                double screenHeight = curScreen.getBounds().getHeight();

                // from-to Array for { Opacity, Width, Height, X, Y }
                double[][] arr = {
                        {1, overlayModel.getOverlayOpacity()},
                        {overlayModel.getOriginalWidth(), overlayModel.getOverlayWidth()},
                        {overlayModel.getOriginalHeight(), overlayModel.getOverlayHeight()},
                        {overlayModel.getOriginalX(), screenWidth - ( overlayModel.getOverlayWidth() + overlayModel.getOverlayXGap() )},
                        {overlayModel.getOriginalY(), screenHeight - ( overlayModel.getOverlayHeight() + overlayModel.getOverlayYGap() )}
                };

                if (!overlayModel.isOverlayState()){
                    for (int i = 0; i < 5; i++) {
                        double temp = arr[i][0];
                        arr[i][0] = arr[i][1];
                        arr[i][1] = temp;
                    }
                }

                boolean allSame = false;
                while(!allSame){
                    allSame = true;
                    for (int i = 0; i < 5; i++) {
                        double from = arr[i][0];
                        double to = arr[i][1];
                        from = (from + to * OverlayModel.RATIO) / (OverlayModel.RATIO + 1);
                        arr[i][0] = from;

                        if ((to - from) > 1.0) allSame = false;
                    }

                    Platform.runLater(() -> {
                        stage.setOpacity(arr[0][0]);
                        stage.setWidth(arr[1][0]);
                        stage.setHeight(arr[2][0]);
                        stage.setX(arr[3][0]);
                        stage.setY(arr[4][0]);
                    });

                    Thread.sleep(OverlayModel.INTERVAL);
                }

                log.info("overlay finished");
                return null;
            }
        };
    }
}
