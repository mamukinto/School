package gui.director;

import gui.Login;
import gui.common.StyleButton;
import gui.common.Colors;
import gui.director.manage.classrooms.ManageClassroomsPanel;
import gui.director.manage.students.ManageStudentsPanel;
import gui.director.manage.subjects.ManageSubjectsPanel;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class DirectorPanel {

    public static void show(Scene scene, Stage stage) {
        stage.setMaximized(true);

        GridPane grid = new GridPane();
        grid.setPrefSize(800,600);
        grid.setStyle("-fx-background-color:" + Colors.MAIN + ";");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);


        Text welcome = new Text("Welcome to director panel");
        welcome.setFill(Color.web(Colors.TEXT.toString()));
        welcome.setFont(new Font(24));


        Button bt1 = new Button("Manage Students");
        Button bt2 = new Button("Manage Teachers");
        Button bt3 = new Button("Manage Classrooms");
        Button bt4 = new Button("Manage Subjects");
        Button back = new Button("Back");

        StyleButton.style(bt1);
        StyleButton.style(bt2);
        StyleButton.style(bt3);
        StyleButton.style(bt4);
        StyleButton.style(back);
        back.setFont(new Font(15));


        bt1.setPrefSize(300,200);
        bt2.setPrefSize(300,200);
        bt3.setPrefSize(300,200);
        bt4.setPrefSize(300,200);


        grid.add(welcome,0,0,3,1);
        grid.add(back,3,0);
        grid.add(bt1,0,1,2,1);
        grid.add(bt2,2,1,2,1);
        grid.add(bt3,0,2,2,1);
        grid.add(bt4,2,2,2,1);
        GridPane.setHalignment(back, HPos.RIGHT);

        bt1.setOnAction(click -> ManageStudentsPanel.show(scene,stage));
//        bt2.setOnAction(click -> ManageTeachersPanel.show(scene, stage));
        bt3.setOnAction(click -> ManageClassroomsPanel.show(scene, stage));
        bt4.setOnAction(click -> ManageSubjectsPanel.show(scene, stage));


        back.setOnAction(click -> Login.login(scene, stage));


        scene.setRoot(grid);
    }

}
