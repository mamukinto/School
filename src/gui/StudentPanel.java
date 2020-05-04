package gui;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import model.Student;

class StudentPanel {
    static void studentPanel(Scene scene, Student student) {
        StackPane root = new StackPane();
        root.getChildren().add(new Text(student.getInfo()));
        scene.setRoot(root);
    }
}
