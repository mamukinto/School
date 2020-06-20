package gui.student;

import gui.GraphicUserInterface;
import gui.Login;
import gui.common.Colors;
import gui.teacher.TableGenerator;
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
import javafx.stage.Stage;
import model.user.student.Student;
import model.user.student.StudentWeekView;
import model.user.student.SubjectWeekViewForStudent;
import service.services.mark.MarkService;
import service.services.mark.MarkServiceImpl;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class JournalPanel {

    private static final MarkService markService = new MarkServiceImpl();

    public static void journalPanel(Student student, Stage stage, Scene scene) {
        VBox root = new VBox();
        scene.setRoot(root);
        scene.getStylesheets().add(GraphicUserInterface.class.getResource("static/css/teacherPanel.css").toExternalForm());
        GridPane header = new GridPane();
        header.getStyleClass().add("header");
        header.setAlignment(Pos.CENTER);

        Text userNameLabel = new Text(student.getFirstName() + " " + student.getLastName());
        userNameLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        userNameLabel.setFill(Color.web(Colors.TEXT.toString()));
        MenuBar menuBar = new MenuBar();
        Menu optionsButton = new Menu("Options");


        MenuItem backToMainPage = new MenuItem("Back to main page");
        MenuItem logOut = new MenuItem("Log out");
        optionsButton.getItems().add(backToMainPage);
        optionsButton.getItems().add(logOut);

        menuBar.getMenus().add(optionsButton);
        menuBar.setPadding(new Insets(5,5,5,5));

        header.add(menuBar,0,0);
        header.add(userNameLabel,1,0);


        root.getChildren().add(header);


        BorderPane borderPane = new BorderPane();
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_LEFT);
        hBox.getStyleClass().add("hbox");

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.getStyleClass().add("datepicker");
        datePicker.setPadding(new Insets(5,5,5,5));

        borderPane.setTop(hBox);
        TableView<SubjectWeekViewForStudent> table = JournalTableGenerator.getTableView(student, getFirstMondayFromDate(datePicker.getValue()),stage);
        table.prefHeightProperty().bind(stage.heightProperty());
        table.prefWidthProperty().bind(stage.widthProperty());

        borderPane.setCenter(table);
        datePicker.setOnAction(action -> {
            TableView<SubjectWeekViewForStudent> table1 = JournalTableGenerator.getTableView(student, getFirstMondayFromDate(datePicker.getValue()),stage);
            table1.prefHeightProperty().bind(stage.heightProperty());
            table1.prefWidthProperty().bind(stage.widthProperty());
            borderPane.setCenter(table1);
            
            markService.updateJournal(student);
        });


        hBox.getChildren().add(datePicker);

        root.getChildren().add(borderPane);

        backToMainPage.setOnAction(click -> {
            StudentPanel.studentPanel(scene, student, stage);
        });

        logOut.setOnAction(click -> {
            Login.login(scene, stage);
        });

    }

    private static LocalDate getFirstMondayFromDate(LocalDate date) {
        for (LocalDate dmb = date; ;dmb = dmb.minusDays(1)) {
            if (dmb.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                return dmb;
            }
        }
    }
}
