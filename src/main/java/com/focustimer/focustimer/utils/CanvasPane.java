package com.focustimer.focustimer.utils;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public class CanvasPane extends Pane {
    private final Canvas canvas;

    public CanvasPane(){
        this(0, 0);
    }

    public CanvasPane(double width, double height) {
        canvas = new Canvas(width, height);
        getChildren().add(canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        final double originalX = snappedLeftInset();
        final double originalY = snappedTopInset();
        final double originalW = snapSizeX(getWidth()) - originalX - snappedRightInset();
        final double originalH = snapSizeY(getHeight()) - originalY - snappedBottomInset();

        final double w = Math.min(originalW, originalH);
        final double h = w;
        final double x = originalX + (originalW - w) / 2;
        final double y = originalY + (originalH - h) / 2;

        canvas.setLayoutX(x);
        canvas.setLayoutY(y);
        canvas.setWidth(w);
        canvas.setHeight(h);
    }

}
