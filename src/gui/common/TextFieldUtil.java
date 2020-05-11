package gui.common;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TextFieldUtil {
    public static TextField getTextField() {
        TextField textfield = new TextField();
        textfield.setStyle("-fx-background-color: " + Colors.SECONDARY + "; -fx-text-inner-color: " + Colors.TEXT + ";");
        textfield.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        return textfield;
    }
}
