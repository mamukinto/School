package gui.director;

import gui.GraphicUserInterface;
import gui.Login;
import gui.common.AlertUtil;
import gui.common.ModalMode;
import gui.common.StyleButton;
import gui.common.Colors;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Student;
import model.exception.SchoolException;
import service.services.auth.AuthService;
import service.services.auth.AuthServiceImpl;
import service.services.classroom.ClassroomsService;
import service.services.classroom.ClassroomsServiceImpl;
import service.services.mark.MarkService;
import service.services.mark.MarkServiceImpl;
import service.services.student.StudentService;
import service.services.student.StudentServiceImpl;
import service.services.subject.SubjectService;
import service.services.subject.SubjectServiceImpl;
import service.services.teacher.TeacherService;
import service.services.teacher.TeacherServiceImpl;

import java.util.Timer;


public class DirectorPanel {

    private static final TeacherService teacherService = new TeacherServiceImpl();

    private static final ClassroomsService classroomsService = new ClassroomsServiceImpl();

    private static final SubjectService subjectService = new SubjectServiceImpl();

    private static final StudentService studentService = new StudentServiceImpl();

    private static final MarkService markService = new MarkServiceImpl();

    private static final AuthService authService = new AuthServiceImpl();



    public static void directorPanel(Scene scene, Stage stage) {
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

        bt1.setOnAction(click -> manageStudents(scene,stage));

        back.setOnAction(click -> Login.login(scene, stage));


        scene.setRoot(grid);
    }

    private static void manageStudents(Scene scene,Stage stage) {
        TableView<Student> table = StudentsTableGenerator.getStudentTableView();
        scene.getStylesheets().add(GraphicUserInterface.class.getResource("static/css/tableStyle.css").toExternalForm());

        BorderPane root = new BorderPane();
        root.setCenter(table);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(40);
        vBox.setPadding(new Insets(0,50,0,50));
        vBox.setStyle("-fx-background-color:" + Colors.MAIN + ";");

        Button back = new Button("Back");
        StyleButton.style(back);
        Button b1 = new Button("Add");
        b1.setPrefSize(150,50);
        StyleButton.style(b1);
        Button b2 = new Button("Remove");
        b2.setPrefSize(150,50);
        StyleButton.style(b2);
        Button b3 = new Button("Edit");
        b3.setPrefSize(150,50);
        StyleButton.style(b3);


        vBox.getChildren().add(back);
        vBox.getChildren().add(b1);
        vBox.getChildren().add(b2);
        vBox.getChildren().add(b3);


        back.setOnAction(click -> directorPanel(scene,stage));
        b1.setOnAction(click -> {
            AddEditStudentStage addEditStudentStage = new AddEditStudentStage(stage,table, ModalMode.ADD);
            addEditStudentStage.show();
        });
        b2.setOnAction(click -> removeStudent(table));
        b3.setOnAction(click -> {
            AddEditStudentStage addEditStudentStage = new AddEditStudentStage(stage,table, ModalMode.EDIT);
            addEditStudentStage.show();
        });


        root.setRight(vBox);
        scene.setRoot(root);
    }

    private static void removeStudent(TableView<Student> tableView) {
        try {
            studentService.removeStudent(tableView.getSelectionModel().getSelectedItem());
            tableView.getItems().setAll(studentService.getStudents());
        } catch (SchoolException e) {
            AlertUtil.alert("Exception","Can't remove student",e.getMessage());
        }
    }


}
