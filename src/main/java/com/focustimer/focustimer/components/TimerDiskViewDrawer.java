package com.focustimer.focustimer.components;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class TimerDiskViewDrawer {

    private GraphicsContext gc;
    private Canvas canvas;
    private double canvasWidth;
    private double canvasHeight;

    private double numRatio = 0.1;
    private double scaleRatio = 0.1;
    private final Effect timerArcShadowEffect = new DropShadow();

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
        this.canvas = gc.getCanvas();
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

    public void drawScale(){
        double gap = (canvasWidth * numRatio) / 2;
        double canvasCenterX = canvasWidth / 2;
        double canvasCenterY = canvasHeight / 2;
        double scaleLength = canvasCenterX * scaleRatio;
        double scaleThickness = scaleLength / 7.5;
        gc.save();

        gc.setFill(Paint.valueOf("black"));
        for (int i = 0; i < 12; i++) {
            gc.translate(canvasCenterX, canvasCenterY);
            gc.rotate(30);
            gc.translate(-canvasCenterX, -canvasCenterY);
            gc.fillRect(canvasCenterX - (scaleThickness / 2),0, scaleThickness, scaleLength);
        }

        for (int i = 0; i < 12 * 5; i++) {
            gc.translate(canvasCenterX, canvasCenterY);
            gc.rotate(30 / 5);
            gc.translate(-canvasCenterX, -canvasCenterY);
            gc.fillRect(canvasCenterX - (scaleThickness / 2),3, 2, 24);
        }

        gc.restore();
    }

    public void drawString(String text){
        double arcRatio = 1 - numRatio - scaleRatio;
        double fontSize = canvasHeight * arcRatio / 3;

        gc.save();

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFill(Paint.valueOf("black"));
        gc.setEffect(timerArcShadowEffect);
        gc.setFont(new Font("Rubik", fontSize));
        gc.fillText(text, canvasWidth / 2, canvasHeight / 2);

        gc.restore();
    }
}
