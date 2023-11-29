package com.focustimer.desktoptimer.component.timerdisk;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;

@Getter
public class CanvasPane extends Pane {
    private final Canvas foreCanvas;
    private final Canvas backCanvas;
    private final TextArea textArea;
    private final Text wrapHelper = new Text();

    public CanvasPane(){
        this(0, 0);
    }

    public CanvasPane(double width, double height) {
        foreCanvas = new Canvas(width, height);
        backCanvas = new Canvas(width, height);

        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setText("Focus");
        textArea.setFont(new Font("Inter", 20));
        textArea.setStyle("-fx-background-color: none");
        textArea.setMinSize(0, 0);

        getChildren().add(backCanvas);
        getChildren().add(foreCanvas);
        getChildren().add(textArea);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        final double originalX = snappedLeftInset();
        final double originalY = snappedTopInset();
        final double originalW = snapSizeX(getWidth()) - originalX - snappedRightInset();
        final double originalH = snapSizeY(getHeight()) - originalY - snappedBottomInset();

        // subtract 1 for preventing infinite layout calling... I don't know it's just javafx bug or not...
        final double w = Math.min(originalW, originalH) - 1;
        final double h = w;
        final double x = originalX + (originalW - w) / 2;
        final double y = originalY + (originalH - h) / 2;

        backCanvas.setLayoutX(x);
        backCanvas.setLayoutY(y);
        backCanvas.setWidth(w);
        backCanvas.setHeight(h);
        foreCanvas.setLayoutX(x);
        foreCanvas.setLayoutY(y);
        foreCanvas.setWidth(w);
        foreCanvas.setHeight(h);
        foreCanvas.toFront();

        wrapHelper.setFont(textArea.getFont());
        wrapHelper.setText(textArea.getText());

        final double wrappedWidth = wrapHelper.getLayoutBounds().getWidth();
        final double wrappedHeight = wrapHelper.getLayoutBounds().getHeight();

        textArea.setPrefSize(wrappedWidth + 40, wrappedHeight + 20);
        textArea.setLayoutX(x + w / 2 - textArea.getPrefWidth() / 2);
        textArea.setLayoutY(y + h / 2 - textArea.getPrefHeight() / 2);
    }
}
