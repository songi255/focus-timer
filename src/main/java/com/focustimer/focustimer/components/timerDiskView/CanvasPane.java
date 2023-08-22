package com.focustimer.focustimer.components.timerDiskView;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lombok.Getter;

@Getter
public class CanvasPane extends Pane {
    private final Canvas canvas;
    private final TextArea textArea;
    private final Text wrapHelper = new Text();

    public CanvasPane(){
        this(0, 0);
    }

    public CanvasPane(double width, double height) {
        canvas = new Canvas(width, height);
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setText("Study");
        textArea.setStyle("-fx-background-color: none");
        textArea.setMinSize(0, 0);

        getChildren().add(canvas);
        getChildren().add(textArea);
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

        wrapHelper.setFont(textArea.getFont());
        wrapHelper.setText(textArea.getText());

        final double wrappedWidth = wrapHelper.getLayoutBounds().getWidth();
        final double wrappedHeight = wrapHelper.getLayoutBounds().getHeight();

        textArea.setPrefSize(wrappedWidth + 40, wrappedHeight + 20);
        textArea.setLayoutX(x + w / 2 - textArea.getPrefWidth() / 2);
        textArea.setLayoutY(y + h / 2 - textArea.getPrefHeight() / 2);
    }
}
