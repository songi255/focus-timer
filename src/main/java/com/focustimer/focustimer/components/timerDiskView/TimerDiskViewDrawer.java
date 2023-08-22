package com.focustimer.focustimer.components.timerDiskView;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import lombok.Getter;
import lombok.Setter;

public class TimerDiskViewDrawer {

    private GraphicsContext gc;
    private double canvasWidth;
    private double canvasHeight;

    @Getter @Setter private double numRatio = 0.15;

    @Getter @Setter private double scaleRatio = 0.1;
    private final Effect timerArcShadowEffect = new DropShadow();

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
        Canvas canvas = gc.getCanvas();
        this.canvasWidth = canvas.getWidth();
        this.canvasHeight = canvas.getHeight();

        canvas.widthProperty().addListener((obs, oldValue, newValue) -> {
            this.canvasWidth = (double) newValue;
        });
        canvas.heightProperty().addListener((obs, oldValue, newValue) -> {
            this.canvasHeight = (double) newValue;
        });
    }

    public void clearCanvas(){
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
    }

    public void drawArc(Paint color, double degree){
        double arcRatio = 1 - numRatio - scaleRatio;
        gc.save();

        gc.setEffect(timerArcShadowEffect);
        gc.setFill(color);
        double arcWidth = canvasWidth * arcRatio;
        double arcHeight = canvasHeight * arcRatio;
        double xGap = (canvasWidth - arcWidth) / 2;
        double yGap = (canvasHeight - arcHeight) / 2;
        gc.fillArc(xGap, yGap, arcWidth, arcHeight, 90, degree, ArcType.ROUND);

        gc.restore();
    }

    public void drawMainScale(){
        double gap = (canvasWidth * numRatio) / 2;
        double canvasCenterX = canvasWidth / 2;
        double canvasCenterY = canvasHeight / 2;
        double scaleLength = canvasCenterX * (1 - numRatio) * scaleRatio;
        double scaleThickness = scaleLength / 7.5;
        gc.save();

        gc.setFill(Paint.valueOf("black"));
        for (int i = 0; i < 12; i++) {
            gc.translate(canvasCenterX, canvasCenterY);
            gc.rotate(30);
            gc.translate(-canvasCenterX, -canvasCenterY);
            gc.fillRect(canvasCenterX - (scaleThickness / 2), gap, scaleThickness, scaleLength);
        }

        gc.restore();
    }

    public void drawSubScale(){
        double gap = (canvasWidth * numRatio) / 2;
        double canvasCenterX = canvasWidth / 2;
        double canvasCenterY = canvasHeight / 2;
        double scaleLength = canvasCenterX * (1 - numRatio) * scaleRatio;
        double scaleThickness = scaleLength / 7.5;
        gc.save();

        gc.setFill(Paint.valueOf("black"));
        for (int i = 0; i < 12 * 5; i++) {
            gc.translate(canvasCenterX, canvasCenterY);
            gc.rotate(30 / 5);
            gc.translate(-canvasCenterX, -canvasCenterY);
            gc.fillRect(canvasCenterX - (scaleThickness / 2), gap, 2, scaleLength / 3 * 2);
        }

        gc.restore();
    }

    public void drawScaleNumber(){
        double canvasCenterX = canvasWidth / 2;
        double canvasCenterY = canvasHeight / 2;

        double fontSize = canvasHeight * numRatio / 2;
        Font font = new Font("Inter", fontSize);

        double radius = canvasWidth / 2 - fontSize / 2;

        gc.save();

        gc.setFont(font);
        gc.setTextBaseline(VPos.CENTER);
        gc.setTextAlign(TextAlignment.CENTER);

        gc.setFill(Paint.valueOf("black"));
        for (int i = 0; i < 12; i++) {
            double radian = Math.PI * i * 30 / 180;
            gc.fillText(String.valueOf(i * 5), canvasCenterX - Math.sin(radian) * radius, canvasCenterY - Math.cos(radian) * radius);
        }
        gc.restore();
    }

    public void drawGoal(String text){
        double arcRatio = 1 - numRatio - scaleRatio;
        double fontSize = canvasHeight * arcRatio / 3 / 2;

        gc.save();

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFill(Paint.valueOf("black"));
        gc.setEffect(timerArcShadowEffect);
        gc.setFont(new Font("Inter", fontSize));
        gc.fillText(text, canvasWidth / 2, canvasHeight / 2);

        gc.restore();
    }
}
