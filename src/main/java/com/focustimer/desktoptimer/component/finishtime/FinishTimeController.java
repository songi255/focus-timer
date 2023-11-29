package com.focustimer.desktoptimer.component.finishtime;

import com.focustimer.desktoptimer.common.Inject;
import com.focustimer.desktoptimer.service.StageService;
import com.focustimer.desktoptimer.viewmodel.TimerViewModel;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static com.focustimer.desktoptimer.util.NewValueListener.listen;

public class FinishTimeController implements Initializable {
    @FXML
    Text finishText;
    AnimationTimer updater;

    private final TimerViewModel timerViewModel;
    private final StageService stageService;

    @Inject
    public FinishTimeController(TimerViewModel timerViewModel, StageService stageService) {
        this.timerViewModel = timerViewModel;
        this.stageService = stageService;

        stageService.isOverlayMode.addListener(listen(isOverlayMode -> {
            if (isOverlayMode) {
                updater.stop();
            } else {
                updater.start();
            }
        }));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        finishText.setFill(Paint.valueOf("white"));
        finishText.setFont(new Font("Inter", 12));

        updater = new AnimationTimer() {
            long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate < 1_0000_0000){
                    return;
                }
                lastUpdate = now;

                LocalDateTime time = LocalDateTime.now();
                LocalDateTime endTime = time.plusSeconds(timerViewModel.curTime.get());
                finishText.setText("~ " +  endTime.format(DateTimeFormatter.ofPattern("hh:mm a")));
            }
        };
        updater.start();
    }
}
