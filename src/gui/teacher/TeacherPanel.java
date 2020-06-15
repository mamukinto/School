package gui.teacher;

import gui.GraphicUserInterface;
import gui.Login;
import gui.common.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.classrom.Classroom;
import model.user.student.StudentWeekView;
import model.user.teacher.Teacher;
import model.exception.SchoolException;
import service.services.classroom.ClassroomsService;
import service.services.classroom.ClassroomsServiceImpl;
import service.services.mark.MarkService;
import service.services.mark.MarkServiceImpl;
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

    private static final MarkService markService = new MarkServiceImpl();

    public static void teacherPanel(Scene scene, Stage stage, Teacher teacher) {
        VBox root = new VBox();
        scene.setRoot(root);
        scene.getStylesheets().add(GraphicUserInterface.class.getResource("static/css/teacherPanel.css").toExternalForm());
        GridPane header = new GridPane();
        header.getStyleClass().add("header");
        header.setAlignment(Pos.CENTER);

        Text userNameLabel = new Text(teacher.getFirstName() + " " + teacher.getLastName());
        userNameLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        userNameLabel.setFill(Color.web(Colors.TEXT.toString()));
        MenuBar menuBar = new MenuBar();
        Menu optionsButton = new Menu("Options");

        MenuItem changePassword = new MenuItem("Change Password");
        MenuItem myInformation = new MenuItem("My information");
        MenuItem logOut = new MenuItem("Log out");
        optionsButton.getItems().add(changePassword);
        optionsButton.getItems().add(myInformation);
        optionsButton.getItems().add(logOut);

        menuBar.getMenus().add(optionsButton);
        menuBar.setPadding(new Insets(5,5,5,5));

        header.add(menuBar,0,0);
        header.add(userNameLabel,1,0);


        root.getChildren().add(header);

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setTabMinWidth(50);

        addClassroomTabs(tabPane, teacher, stage);

        root.getChildren().add(tabPane);

        changePassword.setOnAction(click -> changePassword(stage,teacher));
        logOut.setOnAction(click -> Login.login(scene,stage));
    }

    private static void addClassroomTabs(TabPane tabPane, Teacher teacher, Stage stage) {
        List<Classroom> classrooms = classroomsService.getClassroomsByTeacher(teacher);
        List<Tab> tabs = new ArrayList<>();
        Map<Tab, Classroom> tabClassroomMap = new HashMap<>();
        classrooms.forEach(classroom -> {
            Tab tempTab = new Tab(classroom.getName());
            tabs.add(tempTab);
            tabClassroomMap.put(tempTab,classroom);
        });
        tabPane.getTabs().addAll(tabs);
        tabClassroomMap.forEach((tab, classroom) -> mainPage(tab, classroom, teacher, stage));
    }

    private static void mainPage(Tab tab, Classroom classroom,Teacher teacher, Stage stage) {
        BorderPane borderPane = new BorderPane();
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        hBox.getStyleClass().add("hbox");

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.getStyleClass().add("datepicker");
        datePicker.setPadding(new Insets(5,5,5,5));

        borderPane.setTop(hBox);
        TableView<StudentWeekView> table = TableGenerator.getTableView(teacher, classroom, getFirstMondayFromDate(datePicker.getValue()),stage);
        table.prefHeightProperty().bind(stage.heightProperty());
        table.prefWidthProperty().bind(stage.widthProperty());

        borderPane.setCenter(table);
        datePicker.setOnAction(action -> {
            borderPane.setCenter(TableGenerator.getTableView(teacher, classroom, getFirstMondayFromDate(datePicker.getValue()), stage));
            markService.updateAllJournals();
        });

        hBox.getChildren().add(datePicker);

        tab.setContent(borderPane);
    }

    private static LocalDate getFirstMondayFromDate(LocalDate date) {
        for (LocalDate dmb = date; ;dmb = dmb.minusDays(1)) {
            if (dmb.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                return dmb;
            }
        }
    }

    private static void changePassword(Stage stage, Teacher teacher) {
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

        Label sceneTitle = new Label("Change password:");
        sceneTitle.setTextFill(Color.web(Colors.TEXT.toString()));
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 22));

        grid.add(sceneTitle,0,0,2,1);

        Label oldPasswordLabel = LabelUtil.getLabel("Old password:");
        TextField oldPassword = TextFieldUtil.getTextField();
        grid.add(oldPasswordLabel,0,1);
        grid.add(oldPassword,1,1);

        Label newPasswordLabel = LabelUtil.getLabel("New password:");
        TextField newPassword = TextFieldUtil.getTextField();
        grid.add(newPasswordLabel,0,2);
        grid.add(newPassword,1,2);

        Label newPasswordLabel2 = LabelUtil.getLabel("Repeat new password:");
        TextField newPassword2 = TextFieldUtil.getTextField();
        grid.add(newPasswordLabel2,0,3);
        grid.add(newPassword2,1,3);

        HBox buttonHbox = new HBox();
        Button submit = new Button("Submit");
        buttonHbox.getChildren().add(submit);
        buttonHbox.setAlignment(Pos.BASELINE_RIGHT);
        StyleButton.style(submit);
        submit.setPrefSize(60,20);
        submit.setFont(new Font(12));
        grid.add(buttonHbox,0,4,2,1);

        submit.setOnAction(click -> {
            if (Integer.parseInt(teacher.getPassword()) == oldPassword.getText().hashCode()) {
                if (newPassword.getText().equals(newPassword2.getText())) {
                    teacher.setPassword("" + newPassword.getText().hashCode());
                    try {
                        teacherService.editTeacher(teacher);
                    } catch (SchoolException e) {
                        AlertUtil.alert("Unexpected exception", "Cant edit teacher", e.getMessage());
                    }
                    modal.close();
                } else {
                    sceneTitle.setText("New passwords doesn't match");
                    sceneTitle.setTextFill(Color.web(Colors.WARNING_TEXT.toString()));
                }
            } else if (oldPassword.getText().equals("")){
                sceneTitle.setText("Enter old password");
                sceneTitle.setTextFill(Color.web(Colors.WARNING_TEXT.toString()));
            } else if (Integer.parseInt(teacher.getPassword()) != oldPassword.getText().hashCode()) {
                sceneTitle.setText("Old Password is incorrect");
                sceneTitle.setTextFill(Color.web(Colors.WARNING_TEXT.toString()));
            }
        });

        modal.setScene(scene);
        modal.show();
    }
}
