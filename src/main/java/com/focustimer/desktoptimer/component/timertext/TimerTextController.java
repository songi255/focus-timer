package com.focustimer.desktoptimer.component.timertext;

import com.focustimer.desktoptimer.common.Inject;
import com.focustimer.desktoptimer.viewmodel.TimerViewModel;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.util.Duration;

import static com.focustimer.desktoptimer.util.NewValueListener.listen;

public class TimerTextController implements Initializable {
    private final int fontSize = 20;
    private final Paint mainFontColor = Paint.valueOf("black");
    private final Paint subFontColor = Paint.valueOf("D9D9D9");

    @FXML
    HBox mainContainer;
    @FXML
    Text mainMinute;
    @FXML
    Text mainColon;
    @FXML
    Text mainSecond;

    @FXML
    HBox pomodoroContainer;
    @FXML
    Text pomodoroMinute;
    @FXML
    Text pomodoroColon;
    @FXML
    Text pomodoroSecond;

    Text currentModeMinute;
    Text currentModeColon;
    Text currentModeSecond;
    FadeTransition colonTransition;
    ScaleTransition scaleUpTransition;
    ScaleTransition scaleDownTransition;

    private final TimerViewModel timerViewModel;

    @Inject
    public TimerTextController(TimerViewModel timerViewModel) {
        this.timerViewModel = timerViewModel;

        scaleUpTransition = new ScaleTransition(Duration.millis(150));
        scaleUpTransition.setToX(1.5);
        scaleUpTransition.setToY(1.5);

        scaleDownTransition = new ScaleTransition(Duration.millis(150));
        scaleDownTransition.setToX(1);
        scaleDownTransition.setToY(1);

        colonTransition = new FadeTransition(Duration.millis(500), currentModeColon);
        colonTransition.setAutoReverse(true);
        colonTransition.setCycleCount(Animation.INDEFINITE);
        colonTransition.setToValue(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timerViewModel.isPomodoroMode.addListener(listen(isPomodoroMode -> {
            if (isPomodoroMode) {
                setPomodoroMode();
            } else {
                setMainMode();
            }
        }));

        timerViewModel.curTime.addListener(listen(currentTime -> {
            setCurrentText((long) currentTime);
        }));

        timerViewModel.mainStartTime.addListener(listen(startTime -> {
            setMainText(startTime.longValue());
        }));

        timerViewModel.pomodoroStartTime.addListener(listen(startTime -> {
            setPomodoroText(startTime.longValue());
        }));

        timerViewModel.isTimerRunning.addListener(listen(isTimerRunning -> {
            if (isTimerRunning) {
                colonTransition.play();
            } else {
                colonTransition.stop();
            }
        }));

        setMainMode();
        setMainText(timerViewModel.mainStartTime.get());
        setPomodoroText(timerViewModel.pomodoroStartTime.get());

        mainContainer.setCursor(Cursor.HAND);
        pomodoroContainer.setCursor(Cursor.HAND);
        mainMinute.setCursor(Cursor.V_RESIZE);
        mainSecond.setCursor(Cursor.V_RESIZE);
        pomodoroMinute.setCursor(Cursor.V_RESIZE);
        pomodoroSecond.setCursor(Cursor.V_RESIZE);

        // scroll listner
        mainMinute.setOnScroll(getScrollHandler(false, true));
        mainSecond.setOnScroll(getScrollHandler(false, false));
        pomodoroMinute.setOnScroll(getScrollHandler(true, true));
        pomodoroSecond.setOnScroll(getScrollHandler(true, false));
    }

    public void setMainMode() {
        currentModeMinute = mainMinute;
        currentModeColon = mainColon;
        currentModeSecond = mainSecond;

        setFont(mainMinute, mainColon, mainSecond, new Font("Inter", fontSize), mainFontColor);
        setFont(pomodoroMinute, pomodoroColon, pomodoroSecond, new Font("Inter", fontSize), subFontColor);

        scaleUpTransition.setNode(mainContainer);
        scaleDownTransition.setNode(pomodoroContainer);
        scaleUpTransition.play();
        scaleDownTransition.play();

        setPomodoroText(timerViewModel.pomodoroStartTime.get());
    }

    public void setPomodoroMode() {
        currentModeMinute = pomodoroMinute;
        currentModeColon = pomodoroColon;
        currentModeSecond = pomodoroSecond;

        setFont(mainMinute, mainColon, mainSecond, new Font("Inter", fontSize), subFontColor);
        setFont(pomodoroMinute, pomodoroColon, pomodoroSecond, new Font("Inter", fontSize), mainFontColor);

        scaleUpTransition.setNode(pomodoroContainer);
        scaleDownTransition.setNode(mainContainer);
        scaleUpTransition.play();
        scaleDownTransition.play();

        setMainText(timerViewModel.mainStartTime.get());
    }

    public void setMainText(long time) {
        mainMinute.setText(minute(time));
        mainSecond.setText(second(time));
    }

    public void setPomodoroText(long time) {
        pomodoroMinute.setText(minute(time));
        pomodoroSecond.setText(second(time));
    }

    public void setCurrentText(long time) {
        currentModeMinute.setText(minute(time));
        currentModeSecond.setText(second(time));
    }

    public void setFont(Text minute, Text colon, Text second, Font font, Paint color) {
        minute.setFont(font);
        minute.setFill(color);
        colon.setFont(font);
        colon.setFill(color);
        second.setFont(font);
        second.setFill(color);
    }

    private String minute(long time) {
        return String.format("%02d", time / 60);
    }

    private String second(long time) {
        return String.format("%02d", time % 60);
    }

    @FXML
    public void handleMainContainerClick(MouseEvent e) {
        timerViewModel.setMainMode();
    }

    @FXML
    public void handlePomodoroContainerClick(MouseEvent e) {
        timerViewModel.setPomodoroMode();
    }

    private EventHandler<ScrollEvent> getScrollHandler(boolean isPomodoro, boolean isMinute) {
        return new EventHandler<>() {
            @Override
            public void handle(ScrollEvent mouseEvent) {
                if (timerViewModel.isTimerRunning.get()) {
                    return;
                }

                boolean isUp = mouseEvent.getDeltaY() > 0;
                long deltaTime = (isMinute ? 60 : 1) * (isUp ? 1 : -1);

                long next;
                if (isPomodoro) {
                    next = timerViewModel.pomodoroStartTime.get() + deltaTime;
                    if (next < 0 || next > timerViewModel.pomodoroMaxTime.get()) {
                        return;
                    }
                    timerViewModel.pomodoroStartTime.set(next);
                    timerViewModel.pomodoroCurTime.set(next);
                } else {
                    next = timerViewModel.mainStartTime.get() + deltaTime;
                    if (next < 0 || next > timerViewModel.mainMaxTime.get()) {
                        return;
                    }
                    timerViewModel.mainStartTime.set(next);
                    timerViewModel.mainCurTime.set(next);
                }
            }
        };
    }
}
