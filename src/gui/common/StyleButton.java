package gui.common;

import gui.common.Colors;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Timer;
import java.util.TimerTask;

public class StyleButton {
    static Timer timer = new Timer();
    public static void style(Button button) {
        button.setTextFill(Color.web(Colors.TEXT.toString()));
        button.setStyle("-fx-background-color:" + Colors.SECONDARY + ";");
        button.setFont(new Font(21));
        button.setOnMouseEntered(enter -> {
            button.setStyle("-fx-background-color:#5b5b5b;");
            TimerTask task = new TimerTask()
            {
                public void run()
                {
                    button.setStyle("-fx-background-color:" + Colors.HOVER + ";");
                }
            };
            timer.schedule(task,100);

        });
        button.setOnMouseExited(exit -> {
            button.setStyle("-fx-background-color:#5b5b5b;");
            TimerTask task = new TimerTask()
            {
                public void run()
                {
                    button.setStyle("-fx-background-color:" + Colors.SECONDARY + ";");
                }
            };
            timer.schedule(task,100);
        });
        button.setOnMousePressed(press -> {
            button.setStyle("-fx-background-color:" + Colors.MAIN + ";");
        });
        button.setOnMouseReleased(release -> {
            button.setStyle("-fx-background-color:" + Colors.SECONDARY + ";");
        });
    }
}
