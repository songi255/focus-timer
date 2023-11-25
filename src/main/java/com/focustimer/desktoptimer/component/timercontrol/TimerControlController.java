package com.focustimer.desktoptimer.component.timercontrol;

import com.focustimer.desktoptimer.common.Inject;
import com.focustimer.desktoptimer.config.ApplicationSetting;
import com.focustimer.desktoptimer.viewmodel.TimerViewModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.focustimer.desktoptimer.util.NewValueListener.listen;

public class TimerControlController implements Initializable {
    private final String ICON_PATH = "/com/focustimer/desktoptimer/icons/";
    private final Image startImage = new Image(getClass().getResource(ICON_PATH + "play.png").toExternalForm());
    private final Image pauseImage = new Image(getClass().getResource(ICON_PATH + "pause.png").toExternalForm());
    private final Image restartImage = new Image(getClass().getResource(ICON_PATH + "restart.png").toExternalForm());
    private final Image volumeOnImage = new Image(getClass().getResource(ICON_PATH + "volume_on.png").toExternalForm());
    private final Image volumeOffImage = new Image(getClass().getResource(ICON_PATH + "volume_off.png").toExternalForm());
    private final TimerViewModel timerViewModel;

    @FXML ImageView btnStartImg;
    @FXML ImageView btnStopImg;
    @FXML ImageView btnSoundImg;

    @Inject
    public TimerControlController(TimerViewModel timerViewModel) {
        this.timerViewModel = timerViewModel;
        // for detecting mouse click setting
        timerViewModel.startTime.addListener(listen(newCurTime -> {
            btnStartImg.setImage(startImage);
        }));

        // detect pomodoro
        timerViewModel.isPomodoroMode.addListener(listen(newCurTime -> {
            btnStartImg.setImage(startImage);
        }));

        timerViewModel.isTimerRunning.addListener(listen(isRunning -> {
            if (isRunning){
                btnStartImg.setImage(pauseImage);
                return;
            }

            if (timerViewModel.curTime.get() == 0){
                btnStartImg.setImage(restartImage);
                return;
            }

            btnStartImg.setImage(startImage);
        }));

        ApplicationSetting.alarmSound.addListener(listen(isSoundOn -> {
            btnSoundImg.setImage(isSoundOn ? volumeOnImage : volumeOffImage);
        }));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSoundImg.setImage(ApplicationSetting.alarmSound.get() ? volumeOnImage : volumeOffImage);
    }

    @FXML private void handleStart(){
        if (timerViewModel.isTimerRunning.get()){
            timerViewModel.pauseTimer();
            return;
        }

        if (timerViewModel.curTime.get() == 0){
            timerViewModel.resetTimer();
            btnStartImg.setImage(startImage);
            return;
        }

        timerViewModel.startTimer();
    }

    @FXML private void handleStop(){
        timerViewModel.stopTimer();
    }

    @FXML private void handleSound(){
        ApplicationSetting.alarmSound.set(!ApplicationSetting.alarmSound.get());
    }
}
