package gui.teacher;

import gui.GraphicUserInterface;
import gui.common.Colors;
import gui.common.LabelUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.user.student.Student;
import model.user.teacher.Teacher;
import service.services.student.StudentService;
import service.services.student.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class StudentInfoModal {

    private static final StudentService studentService = new StudentServiceImpl();

    public static void show(Stage stage, Student student, Teacher teacher ) {
        Stage modal = new Stage();
        modal.setWidth(500);
        modal.setHeight(300);
        modal.initOwner(stage);
        modal.initModality(Modality.WINDOW_MODAL);

        GridPane grid = new GridPane();
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(GraphicUserInterface.class.getResource("static/css/login.css").toExternalForm());
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 50, 50, 50));
        grid.setStyle("-fx-background-color: " + Colors.MAIN + ";");
        grid.setPrefSize(500, 300);

        Label personalIdLabel = LabelUtil.getLabel("Personal Id: " + student.getPersonalId());
        Label firstNameLabel = LabelUtil.getLabel("First name: " + student.getFirstName());
        Label lastNameLabel = LabelUtil.getLabel("Last Name: " + student.getLastName());
        Label email = LabelUtil.getLabel("Email: " + student.getEmail());
        Label averageMark = LabelUtil.getLabel("Average mark: " + studentService.getAverageMarkOfStudentBySubject(student,teacher.getSubject()));

        grid.add(personalIdLabel,0,0);
        grid.add(firstNameLabel,0,1);
        grid.add(lastNameLabel,0,2);
        grid.add(email,0,3);
        grid.add(averageMark,0,4);

        modal.setScene(scene);
        modal.show();
    }

}