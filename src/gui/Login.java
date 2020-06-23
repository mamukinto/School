package gui;

import gui.common.Colors;
import gui.director.DirectorPanel;
import gui.student.StudentPanel;
import gui.teacher.TeacherPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.user.User;
import model.user.student.Student;
import model.user.teacher.Teacher;
import model.user.UserType;
import model.exception.SchoolException;
import service.services.auth.AuthService;
import service.services.auth.AuthServiceImpl;
import service.services.student.StudentService;
import service.services.student.StudentServiceImpl;

public class Login {
    private static final AuthService auth = new AuthServiceImpl();

    public static void login(Scene scene, Stage stage) {
        stage.getIcons().add(new Image("file:icon.png"));
        scene.getStylesheets().removeAll(scene.getStylesheets());
        scene.getStylesheets().add(GraphicUserInterface.class.getResource("static/css/login.css").toExternalForm());
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 50, 50, 50));
        grid.setStyle("-fx-background-color: " + Colors.MAIN + ";");
        grid.setPrefSize(500, 300);
//        stage.setResizable(false);
        stage.setMaximized(true);
 

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setFill(Color.web(Colors.TEXT.toString()));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userType = new Label("User Type:");
        userType.setTextFill(Color.web(Colors.TEXT.toString()));
        userType.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(userType, 0, 1);


        ComboBox<String> cb = new ComboBox<>();
        cb.setStyle("-fx-background-color: " + Colors.SECONDARY + "; -fx-mark-color: " + Colors.SECONDARY + ";");


        cb.setValue("Student");
        cb.setEditable(false);


        cb.getItems().add("Director");
        cb.getItems().add("Teacher");
        cb.getItems().add("Student");
        grid.add(cb, 1, 1);

        Label userName = new Label("Personal ID:");
        userName.setTextFill(Color.web(Colors.TEXT.toString()));
        userName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(userName, 0, 2);

        TextField userTextField = new TextField();
        userTextField.setStyle("-fx-background-color: " + Colors.SECONDARY + "; -fx-text-inner-color: " + Colors.TEXT + ";");
        userTextField.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(userTextField, 1, 2);

        Label pw = new Label("Password:");
        pw.setTextFill(Color.web(Colors.TEXT.toString()));
        pw.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(pw, 0, 3);

        PasswordField pwBox = new PasswordField();
        pwBox.setStyle("-fx-background-color: " + Colors.SECONDARY + "; -fx-text-inner-color: " + Colors.TEXT + ";");
        grid.add(pwBox, 1, 3);

        Button button = new Button("Sign in");
        button.setStyle("-fx-background-color: " + Colors.SECONDARY + ";");
        button.setTextFill(Color.web(Colors.TEXT.toString()));
        button.setPrefSize(70,25);

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(button);
        grid.add(hbBtn, 1, 4);




        button.setDefaultButton(true);
        button.setOnMouseEntered(enter -> {
            button.setStyle("-fx-background-color:" + Colors.HOVER + ";");
        });
        button.setOnMouseExited(exit -> {
            button.setStyle("-fx-background-color:" + Colors.SECONDARY + ";");
        });


        button.setOnAction(click1 -> {
            switch (cb.getValue()) {
                case "Director":
                    loginDirector(userTextField, pwBox, scenetitle, scene, stage);
                    break;
                case "Teacher":
                    loginTeacher(userTextField, pwBox, scenetitle, scene, stage);
                    break;
                case "Student":
                    loginStudent(userTextField, pwBox, scenetitle, scene, stage);
                    break;
                default:
                    scenetitle.setFill(Color.web(Colors.WARNING_TEXT.toString()));
                    scenetitle.setText("Wrong user id or password");
                    scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
                    break;

            }
        });

        scene.setRoot(grid);
    }

    private static void loginDirector(TextField userTextField, PasswordField pwBox, Text scenetitle, Scene scene,Stage stage) {
        try {
            String personalId = userTextField.getText();
            String password = pwBox.getText();
            User director = auth.auth(personalId, password, UserType.DIRECTOR);
            if (director == AuthServiceImpl.director) {
                DirectorPanel.show(scene,stage);
            } else {
                scenetitle.setFill(Color.web(Colors.WARNING_TEXT.toString()));
                scenetitle.setText("Wrong user id or password");
                scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
            }
        } catch (SchoolException e) {
            System.out.println("Unexpected problem - " + e.getMessage());
        }
    }

    private static void loginTeacher(TextField userTextField, PasswordField pwBox, Text scenetitle, Scene scene, Stage stage) {
        try {
            String personalId = userTextField.getText();
            String password = pwBox.getText();
            Teacher teacher = (Teacher) auth.auth(personalId, password, UserType.TEACHER);
            if (teacher != null) {
                TeacherPanel.teacherPanel(scene,stage,teacher);
            } else {
                scenetitle.setFill(Color.web(Colors.WARNING_TEXT.toString()));
                scenetitle.setText("Wrong user id or password");
                scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
            }
        } catch (SchoolException e) {
            System.out.println("Unexpected problem - " + e.getMessage());
        }
    }


    private static void loginStudent(TextField userTextField, PasswordField pwBox, Text scenetitle, Scene scene,  Stage stage) {
        try {
            String personalId = userTextField.getText();
            String password = pwBox.getText();
            Student student = (Student) auth.auth(personalId, password, UserType.STUDENT);
            if (student != null) {
                StudentPanel.studentPanel(scene, student , stage);
            } else {
                scenetitle.setFill(Color.web(Colors.WARNING_TEXT.toString()));
                scenetitle.setText("Wrong user id or password");
                scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
            }
        } catch (SchoolException e) {
            System.out.println("Unexpected problem - " + e.getMessage());
        }
    }


}