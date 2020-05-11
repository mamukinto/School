package gui.common;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LabelUtil {
    public static Label getLabel(String text) {
        Label label = new Label(text);
        label.setTextFill(Color.web(Colors.TEXT.toString()));
        label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        return label;
    }
}
