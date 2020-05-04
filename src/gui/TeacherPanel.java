package gui;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import model.Teacher;

class TeacherPanel {
    static void teacherPanel(Scene scene, Teacher teacher) {
        StackPane root = new StackPane();
        root.getChildren().add(new Text(teacher.getInfo()));
        scene.setRoot(root);
    }
}
