package gui.common;

import javafx.scene.control.Alert;

public class AlertUtil {
    public static void alert(String title,String header,String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
