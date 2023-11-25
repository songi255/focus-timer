package com.focustimer.desktoptimer.component.timerdisk;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import static com.focustimer.desktoptimer.util.NewValueListener.listen;
import static javafx.beans.binding.Bindings.divide;
import static javafx.beans.binding.Bindings.subtract;
import static javafx.beans.binding.Bindings.multiply;
import static javafx.beans.binding.Bindings.add;

public class TimerDiskDrawer {

    private Canvas foreCanvas;
    private Canvas backCanvas;
    private GraphicsContext foreCanvasGc;
    private GraphicsContext backCanvasGc;
    private TimerDiskSetting setting;

    private final DoubleProperty canvasWidth = new SimpleDoubleProperty();
    private final DoubleProperty canvasHeight = new SimpleDoubleProperty();
    private final DoubleProperty centerX = new SimpleDoubleProperty();
    private final DoubleProperty centerY = new SimpleDoubleProperty();
    private final IntegerProperty mainScaleCount = new SimpleIntegerProperty();
    private final IntegerProperty subScaleCount = new SimpleIntegerProperty();
    private final DoubleProperty mainScaleRotationGap = new SimpleDoubleProperty();
    private final DoubleProperty subScaleRotationGap = new SimpleDoubleProperty();

    // additional optimization field but don't need at this time.
    private final DoubleProperty mainScaleLength = new SimpleDoubleProperty();
    private final DoubleProperty subScaleLength = new SimpleDoubleProperty();
    private final DoubleProperty mainScaleThickness = new SimpleDoubleProperty();
    private final DoubleProperty subScaleThickness = new SimpleDoubleProperty();

    // separate arc properties for performance
    private final DoubleProperty arcSizeRatio = new SimpleDoubleProperty();
    private final DoubleProperty arcWidth = new SimpleDoubleProperty();
    private final DoubleProperty arcHeight = new SimpleDoubleProperty();
    private final DoubleProperty arcXGap = new SimpleDoubleProperty();
    private final DoubleProperty arcYGap = new SimpleDoubleProperty();

    private Paint mainScaleColor;
    private Paint subScaleColor;
    private Paint textColor;
    private final Effect timerArcShadowEffect = new DropShadow();

    public void setCanvas(CanvasPane canvasPane) {
        this.foreCanvas = canvasPane.getForeCanvas();
        this.backCanvas = canvasPane.getBackCanvas();
        this.foreCanvasGc = foreCanvas.getGraphicsContext2D();
        this.backCanvasGc = backCanvas.getGraphicsContext2D();

        canvasWidth.bind(foreCanvas.widthProperty());
        canvasHeight.bind(foreCanvas.heightProperty());
        centerX.bind(divide(foreCanvas.widthProperty(), 2));
        centerY.bind(divide(foreCanvas.heightProperty(), 2));
        mainScaleCount.bind(setting.mainScaleCount);
        subScaleCount.bind(setting.subScaleCount);
        mainScaleRotationGap.bind(divide(360.0, mainScaleCount));
        subScaleRotationGap.bind(divide(360.0, subScaleCount));
    }

    public void setTimerDiskSetting(TimerDiskSetting setting) {
        this.setting = setting;
        mainScaleColor = Paint.valueOf(setting.mainScaleColor.get());
        subScaleColor = Paint.valueOf(setting.subScaleColor.get());
        textColor = Paint.valueOf(setting.textColor.get());
        setting.mainScaleColor.addListener(listen(color -> mainScaleColor = Paint.valueOf(color)));
        setting.subScaleColor.addListener(listen(color -> subScaleColor = Paint.valueOf(color)));
        setting.textColor.addListener(listen(color -> textColor = Paint.valueOf(color)));

        // arc binding for performance
        arcSizeRatio.bind(subtract(
                subtract(1, setting.numberSizeRatio),
                setting.scaleSizeRatio
        ));
        arcWidth.bind(multiply(canvasWidth, arcSizeRatio));
        arcHeight.bind(multiply(canvasHeight, arcSizeRatio));
        arcXGap.bind(divide(
                subtract(canvasWidth, arcWidth),
                2
        ));
        arcYGap.bind(divide(
                subtract(canvasHeight, arcHeight),
                2
        ));
    }

    public void clearForeCanvas() {
        foreCanvasGc.clearRect(0, 0, canvasWidth.get(), canvasHeight.get());
    }

    public void clearBackCanvas() {
        backCanvasGc.clearRect(0, 0, canvasWidth.get(), canvasHeight.get());
    }

    public void drawArc(Paint color, double degree) {
        backCanvasGc.save();
        backCanvasGc.setFill(color);
        backCanvasGc.setEffect(timerArcShadowEffect);
        backCanvasGc.fillArc(
                arcXGap.get(), arcYGap.get(),
                arcWidth.get(), arcHeight.get(),
                90, degree,
                ArcType.ROUND);
        backCanvasGc.restore();
    }

    public void drawMainScale() {
        double numRatio = setting.numberSizeRatio.get();
        double scaleRatio = setting.scaleSizeRatio.get();

        double gap = (canvasWidth.get() * numRatio) / 2;
        double scaleLength = centerX.get() * (1 - numRatio) * scaleRatio * 0.8;
        double scaleThickness = scaleLength / 5;
        gap += scaleLength * 0.1;

        foreCanvasGc.save();
        foreCanvasGc.setFill(mainScaleColor);
        for (int i = 0; i < setting.mainScaleCount.get(); i++) {
            foreCanvasGc.translate(centerX.get(), centerY.get());
            foreCanvasGc.rotate(mainScaleRotationGap.get());
            foreCanvasGc.translate(-centerX.get(), -centerY.get());
            foreCanvasGc.fillRect(centerX.get() - (scaleThickness / 2), gap, scaleThickness, scaleLength);
        }
        foreCanvasGc.restore();
    }

    public void drawSubScale() {
        double numRatio = setting.numberSizeRatio.get();
        double scaleRatio = setting.scaleSizeRatio.get();

        double gap = (canvasWidth.get() * numRatio) / 2;
        double scaleLength = centerX.get() * (1 - numRatio) * scaleRatio;
        double scaleThickness = scaleLength / 7.5;

        foreCanvasGc.save();
        foreCanvasGc.setFill(subScaleColor);
        for (int i = 0; i < setting.subScaleCount.get(); i++) {
            foreCanvasGc.translate(centerX.get(), centerY.get());
            foreCanvasGc.rotate(subScaleRotationGap.get());
            foreCanvasGc.translate(-centerX.get(), -centerY.get());
            foreCanvasGc.fillRect(centerX.get() - (scaleThickness / 2), gap, 2, scaleLength / 3 * 2);
        }
        foreCanvasGc.restore();
    }

    public void drawScaleNumber(double maxTime) {
        double numRatio = setting.numberSizeRatio.get();

        int max = (int) maxTime / 60;
        int gap = max / mainScaleCount.get();
        double degree = 360.0 / mainScaleCount.get();

        double fontSize = canvasHeight.get() * numRatio / 2;
        Font font = new Font("Inter", fontSize);

        double radius = canvasWidth.get() / 2 - fontSize / 2;

        foreCanvasGc.save();
        foreCanvasGc.setFont(font);
        foreCanvasGc.setTextBaseline(VPos.CENTER);
        foreCanvasGc.setTextAlign(TextAlignment.CENTER);
        foreCanvasGc.setFill(textColor);
        for (int i = 0; i < setting.mainScaleCount.get(); i++) {
            double radian = Math.PI * i * degree / 180;
            foreCanvasGc.fillText(String.valueOf(i * gap),
                    centerX.get() - Math.sin(radian) * radius,
                    centerY.get() - Math.cos(radian) * radius);
        }
        foreCanvasGc.restore();
    }

    public void drawText(String text) {
        double fontSize = canvasHeight.get() * arcSizeRatio.get() / 3 / 2;

        foreCanvasGc.save();

        foreCanvasGc.setTextAlign(TextAlignment.CENTER);
        foreCanvasGc.setTextBaseline(VPos.CENTER);
        foreCanvasGc.setFill(textColor);
        foreCanvasGc.setEffect(timerArcShadowEffect);
        foreCanvasGc.setFont(new Font("Inter", fontSize));
        foreCanvasGc.fillText(text, canvasWidth.get() / 2, canvasHeight.get() / 2);

        foreCanvasGc.restore();
    }
}
