package gui.student;

import gui.common.LabelUtil;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Event;
import model.user.student.Student;
import service.services.student.StudentService;
import service.services.student.StudentServiceImpl;
import service.services.subject.SubjectService;
import service.services.subject.SubjectServiceImpl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentPanel {

    private static StudentService studentService = new StudentServiceImpl();

    private static SubjectService subjectService = new SubjectServiceImpl();

    public static void studentPanel(Scene scene, Student student, Stage stage) {
        stage.setMaximized(true);
        BorderPane root = new BorderPane();
        scene.setRoot(root);
        VBox main = new VBox();
        VBox rightPanel = new VBox();
        root.setCenter(main);
        root.setRight(rightPanel);

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Options");
        menuBar.getMenus().add(menu);
        MenuItem changePassword = new MenuItem("Change password");
        MenuItem viewMyInfo = new MenuItem("View my information");
        MenuItem logOut = new MenuItem("Log out");
        menu.getItems().addAll(changePassword,viewMyInfo,logOut);
        main.getChildren().add(menuBar);
        Label sceneTitle = LabelUtil.getLabel("What's new?");
        VBox feed = new VBox();
        main.getChildren().add(sceneTitle);
        main.getChildren().add(feed);


        List<Text> feedEvents = new ArrayList<>();
        student.getEvents().forEach(event -> {
            feedEvents.add(eventToText(event));
        });
        feed.getChildren().addAll(feedEvents);

        Label rightPanelTitle = LabelUtil.getLabel(student.getFirstName() + " " + student.getLastName());
        rightPanel.getChildren().add(rightPanelTitle);


        subjectService.getSubjects().forEach(subject -> {
            rightPanel.getChildren().add(new Text(subject.getName() + " average mark: " + studentService.getAverageMarkOfStudentBySubject(student,subject)));
        });

    }

    private static Text eventToText(Event event) {
        Text eventText = new Text();
        eventText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 17));

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH-mm-ss");
        String date = event.getDate().format(dateFormat);

        eventText.setText(event.getContent() + "  " + date);
        return eventText;
    }

}
