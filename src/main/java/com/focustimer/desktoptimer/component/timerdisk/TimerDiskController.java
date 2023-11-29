package com.focustimer.desktoptimer.component.timerdisk;

import com.focustimer.desktoptimer.common.Inject;
import com.focustimer.desktoptimer.service.StageService;
import com.focustimer.desktoptimer.viewmodel.TimerViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Paint;

import static com.focustimer.desktoptimer.util.NewValueListener.listen;

public class TimerDiskController implements Initializable {
    @FXML
    CanvasPane timerCanvasContainer;

    private final TimerViewModel timerViewModel;
    private final StageService stageService;
    private final TimerDiskSetting setting = new TimerDiskSetting();
    private final TimerDiskDrawer drawer = new TimerDiskDrawer();
    private final TimerDiskMouseHandler mouseHandler;

    private Paint mainTimerColor = Paint.valueOf(setting.mainTimerColor.get());
    private Paint pomodoroTimerColor = Paint.valueOf(setting.pomodoroTimerColor.get());

    @Inject
    public TimerDiskController(TimerViewModel timerViewModel, StageService stageService) {
        this.timerViewModel = timerViewModel;
        this.stageService = stageService;
        this.mouseHandler = new TimerDiskMouseHandler(timerViewModel);

        timerViewModel.curTime.addListener(listen(this::drawTimerArc));
        timerViewModel.maxTime.addListener(listen(newMaxTime -> {
            drawTimerGuide();
        }));
        timerViewModel.isPomodoroMode.addListener(listen(isPomodoro -> {
            timerCanvasContainer.getTextArea().setText(timerViewModel.timerName.get());
        }));

        stageService.isOverlayMode.addListener(listen(this::drawTimerGuide));

        setting.mainTimerColor.addListener(listen(newColor -> {
            mainTimerColor = Paint.valueOf(newColor);
        }));
        setting.pomodoroTimerColor.addListener(listen(newColor -> {
            pomodoroTimerColor = Paint.valueOf(newColor);
        }));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Canvas guideCanvas = timerCanvasContainer.getForeCanvas();
        drawer.setTimerDiskSetting(setting);
        drawer.setCanvas(timerCanvasContainer);

        guideCanvas.widthProperty().addListener(listen(this::drawTimer));
        guideCanvas.heightProperty().addListener(listen(this::drawTimer));

        mouseHandler.setTimerDiskSetting(setting);
        mouseHandler.setCanvas(timerCanvasContainer.getForeCanvas());
        guideCanvas.setOnMousePressed(mouseHandler::canvasMouseHandler);
        guideCanvas.setOnMouseDragged(mouseHandler::canvasMouseHandler);
        timerCanvasContainer.setOnScroll(mouseHandler::handleMouseScrollEvent);

        TextArea textArea = timerCanvasContainer.getTextArea();
        textArea.textProperty().bindBidirectional(timerViewModel.timerName);
    }

    public void drawTimerGuide() {
        boolean isOverlayMode = stageService.isOverlayMode.get();

        TextArea textArea = timerCanvasContainer.getTextArea();
        textArea.setVisible(!isOverlayMode);
        textArea.setManaged(!isOverlayMode);
        // deprive focus from textArea
        timerCanvasContainer.requestFocus();

        drawer.clearForeCanvas();
        drawer.drawMainScale();
        if (isOverlayMode) {
            drawer.drawText(timerViewModel.timerName.get());
        } else {
            drawer.drawSubScale();
            drawer.drawScaleNumber(timerViewModel.maxTime.get());
        }
    }

    public void drawTimerArc() {
        Paint color = timerViewModel.isPomodoroMode.get() ? pomodoroTimerColor : mainTimerColor;
        drawer.clearBackCanvas();
        drawer.drawArc(color, (double) timerViewModel.curTime.get() / timerViewModel.maxTime.get() * 360.0);
    }

    public void drawTimer() {
        drawTimerGuide();
        drawTimerArc();
    }
}
