package gui.teacher;

import gui.Login;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Classroom;
import model.Teacher;
import service.services.classroom.ClassroomsService;
import service.services.classroom.ClassroomsServiceImpl;
import service.services.teacher.TeacherService;
import service.services.teacher.TeacherServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherPanel {

    private static final TeacherService teacherService = new TeacherServiceImpl();

    private static final ClassroomsService classroomsService = new ClassroomsServiceImpl();

    public static void teacherPanel(Scene scene, Stage stage, Teacher teacher) {
        VBox root = new VBox();
        scene.setRoot(root);

        HBox header = new HBox();
        header.setAlignment(Pos.BASELINE_RIGHT);

        Label userNameLabel = new Label(teacher.getFirstName() + " " + teacher.getLastName());

        ComboBox<String> menu = new ComboBox<>();
        menu.setValue("*");
        menu.getItems().add("Change password");
        menu.getItems().add("My info");
        menu.getItems().add("Log out");

        header.getChildren().add(userNameLabel);
        header.getChildren().add(menu);

        root.getChildren().add(header);

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setTabMinWidth(50);

        addClassroomTabs(tabPane, teacher);

        root.getChildren().add(tabPane);

        menu.setOnAction(click -> {
            switch(menu.getValue()) {
                case "Log out":
                    Login.login(scene,stage);
                    break;
                case "My info":
                    // code block
                    break;
                case "Change password":
                    //code block
                    break;
                default:
                    // code block
            }
        });

    }

    private static void addClassroomTabs(TabPane tabPane, Teacher teacher) {
        List<Classroom> classrooms = classroomsService.getClassroomsByTeacher(teacher);
        List<Tab> tabs = new ArrayList<>();
        Map<Tab, Classroom> tabClassroomMap = new HashMap<>();
        classrooms.forEach(classroom -> {
            Tab tempTab = new Tab(classroom.getName());
            tabs.add(tempTab);
            tabClassroomMap.put(tempTab,classroom);
        });
        tabPane.getTabs().addAll(tabs);
        tabClassroomMap.forEach((tab, classroom) -> mainPage(classroom,tab));
    }

    private static void mainPage(Classroom classroom, Tab tab) {
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_LEFT);
        TableView<String> table = new TableView<>();
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(table);

        ComboBox<String> weeksCB = new ComboBox<>();
        weeksCB.setPromptText("Week");

        TextField searchTF = new TextField();
        searchTF.setPromptText("Search...");

        hBox.getChildren().add(weeksCB);
        hBox.getChildren().add(searchTF);

        tab.setContent(vBox);
    }

}
