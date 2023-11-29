package com.focustimer.desktoptimer.util;

import com.focustimer.desktoptimer.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Window;

public class Notification {
    public static void playAlertSound(){
        new AudioClip(Application.class.getResource("audios/notification.mp3").toString())
                .play();
    }

    public static void showMessage(Window owner, String message){
        Tooltip tooltip = new Tooltip(message);
        tooltip.setFont(new Font("Inter", 24));
        tooltip.setText("Focus Timer" + System.lineSeparator() + message);

        ImageView imageView = new ImageView(Application.class.getResource("icons/app_icon.png").toExternalForm());
        imageView.setFitWidth(48);
        imageView.setFitHeight(48);
        tooltip.setGraphic(imageView);
        tooltip.setGraphicTextGap(12);

        double tooltipWidth = 300;
        double tooltipHeight = 80;
        double windowXGap = 36;
        double windowYGap = 48;
        tooltip.setAutoHide(true);
        tooltip.setMinWidth(tooltipWidth);
        tooltip.setMinHeight(tooltipHeight);

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenX = screenBounds.getWidth() - tooltipWidth - windowXGap;
        double screenY = screenBounds.getHeight() - tooltipHeight - windowYGap;

//        tooltip.show(owner, screenX, screenY);
        tooltip.show(owner);
    }
}
