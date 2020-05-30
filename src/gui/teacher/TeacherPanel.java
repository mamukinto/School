package gui.teacher;

import gui.Login;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Classroom;
import model.StudentWeekView;
import model.Teacher;
import service.services.classroom.ClassroomsService;
import service.services.classroom.ClassroomsServiceImpl;
import service.services.teacher.TeacherService;
import service.services.teacher.TeacherServiceImpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
        tabClassroomMap.forEach((tab, classroom) -> mainPage(tab, classroom, teacher));
    }

    private static void mainPage(Tab tab, Classroom classroom,Teacher teacher) {
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_LEFT);



        DatePicker datePicker = new DatePicker();


        TextField searchTF = new TextField();
        searchTF.setPromptText("Search...");

        vBox.getChildren().add(hBox);
        TableView<StudentWeekView> table = TableGenerator.getTableView(teacher, classroom,searchTF.getText(),getFirstMondayFromDate(datePicker.getValue()));
        vBox.getChildren().add(table);


        hBox.getChildren().add(datePicker);
        hBox.getChildren().add(searchTF);




        tab.setContent(vBox);
    }

    private static LocalDate getFirstMondayFromDate(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        for (LocalDate dmb = date; ;dmb = dmb.minusDays(1)) {
            if (dmb.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                return dmb;
            }
        }
    }

}
