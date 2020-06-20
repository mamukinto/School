package gui.teacher;

import gui.GraphicUserInterface;
import gui.common.Colors;
import gui.common.LabelUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.user.student.Student;
import model.user.teacher.Teacher;
import service.services.student.StudentService;
import service.services.student.StudentServiceImpl;

public class StudentInfoModal {

    private static final StudentService studentService = new StudentServiceImpl();

    public static void show(Stage stage, Student student, Teacher teacher ) {
        Stage modal = new Stage();
        modal.setWidth(500);
        modal.setHeight(300);
        modal.initOwner(stage);
        modal.initModality(Modality.WINDOW_MODAL);

        VBox vBox = new VBox(20);
        GridPane grid = new GridPane();
        Scene scene = new Scene(vBox);
        scene.getStylesheets().add(GraphicUserInterface.class.getResource("static/css/login.css").toExternalForm());
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 50, 50, 50));
        grid.setStyle("-fx-background-color: " + Colors.MAIN + ";");
        vBox.setStyle("-fx-background-color: " + Colors.MAIN + ";");
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20,0,0,0));
        grid.setPrefSize(500, 300);


        Label sceneTitle = LabelUtil.getLabel(student.getFirstName() + " information");
        sceneTitle.setFont(new Font(22));
        vBox.getChildren().add(sceneTitle);
        vBox.getChildren().add(grid);

        Label personalIdLabel = LabelUtil.getLabel("Personal Id");
        Label personalId = LabelUtil.getLabel(":   " + student.getPersonalId());
        Label firstNameLabel = LabelUtil.getLabel("First name");
        Label firstName = LabelUtil.getLabel(":   " + student.getFirstName());
        Label lastNameLabel = LabelUtil.getLabel("Last Name");
        Label lastName = LabelUtil.getLabel(":   " + student.getLastName());
        Label emailLabel = LabelUtil.getLabel("Email");
        Label email = LabelUtil.getLabel(":   " + student.getEmail());
        Label averageMarkLabel = LabelUtil.getLabel("Average mark");
        Label averageMark = LabelUtil.getLabel(":   " + studentService.getAverageMarkOfStudentBySubject(student,teacher.getSubject()));


        grid.add(personalIdLabel,0,0);
        grid.add(personalId,1,0);
        grid.add(firstNameLabel,0,1);
        grid.add(firstName,1,1);
        grid.add(lastNameLabel,0,2);
        grid.add(lastName,1,2);
        grid.add(emailLabel,0,3);
        grid.add(email,1,3);
        grid.add(averageMarkLabel,0,4);
        grid.add(averageMark,1,4);

        modal.setScene(scene);
        modal.show();
    }

}