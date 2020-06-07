package gui.student;

import gui.Login;
import gui.common.StyleButton;
import gui.common.Colors;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.user.student.Student;

public class StudentPanel {
    public static void studentPanel(Scene scene, Student student, Stage stage) {
        stage.setMaximized(true);
        GridPane grid = new GridPane();
        grid.setPrefSize(800,600);
        grid.setStyle("-fx-background-color:" + Colors.MAIN + ";");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);


        Text welcome = new Text("Welcome to student panel " + student.getFirstName());
        welcome.setFill(Color.web(Colors.TEXT.toString()));
        welcome.setFont(new Font(24));


        Button bt1 = new Button("Manage profile");
        Button bt2 = new Button("View marks");

        Button back = new Button("Back");

        StyleButton.style(bt1);
        StyleButton.style(bt2);
        StyleButton.style(back);
        back.setFont(new Font(15));



        bt1.setPrefSize(300,200);
        bt2.setPrefSize(300,200);

        back.setOnAction(click -> {
            Login.login(scene, stage);
        });




        grid.add(welcome,0,0,3,1);
        grid.add(back,3,0);
        grid.add(bt1,0,1,2,1);
        grid.add(bt2,2,1,2,1);
        GridPane.setHalignment(back, HPos.RIGHT);

        scene.setRoot(grid);
    }
}
