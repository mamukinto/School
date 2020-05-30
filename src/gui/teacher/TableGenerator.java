package gui.teacher;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Classroom;
import model.StudentWeekView;
import model.Teacher;
import service.services.teacher.TeacherService;
import service.services.teacher.TeacherServiceImpl;

import java.time.LocalDate;

public class TableGenerator {

    private final static TeacherService teacherService = new TeacherServiceImpl();

    public static TableView<StudentWeekView> getTableView(Teacher teacher, Classroom classroom, String searchName, LocalDate from) {
        TableView<StudentWeekView> studentWeekViewTableView = new TableView<>();
        studentWeekViewTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        studentWeekViewTableView.setPlaceholder(new Label("No Students in this classroom yet."));

        TableColumn<StudentWeekView, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.prefWidthProperty().bind(studentWeekViewTableView.widthProperty().multiply(0.25));


        TableColumn<StudentWeekView, Integer> mondayColumn = new TableColumn<>("Monday");
        mondayColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        mondayColumn.setCellValueFactory(new PropertyValueFactory<>("mondayMark"));
        mondayColumn.prefWidthProperty().bind(studentWeekViewTableView.widthProperty().multiply(0.15));

        TableColumn<StudentWeekView, Integer> wednesdayColumn = new TableColumn<>("Wednesday");
        wednesdayColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        wednesdayColumn.setCellValueFactory(new PropertyValueFactory<>("wednesdayMark"));
        wednesdayColumn.prefWidthProperty().bind(studentWeekViewTableView.widthProperty().multiply(0.15));

        TableColumn<StudentWeekView, Integer> thursdayColumn = new TableColumn<>("Thursday");
        thursdayColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        thursdayColumn.setCellValueFactory(new PropertyValueFactory<>("thursdayMark"));
        thursdayColumn.prefWidthProperty().bind(studentWeekViewTableView.widthProperty().multiply(0.15));

        TableColumn<StudentWeekView, Integer> fridayColumn = new TableColumn<>("Friday");
        fridayColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        fridayColumn.setCellValueFactory(new PropertyValueFactory<>("fridayMark"));
        fridayColumn.prefWidthProperty().bind(studentWeekViewTableView.widthProperty().multiply(0.15));

        studentWeekViewTableView.getColumns().add(nameColumn);
        studentWeekViewTableView.getColumns().add(mondayColumn);
        studentWeekViewTableView.getColumns().add(wednesdayColumn);
        studentWeekViewTableView.getColumns().add(thursdayColumn);
        studentWeekViewTableView.getColumns().add(fridayColumn);


        studentWeekViewTableView.getItems().addAll(getStudentWeekViews(teacher, classroom, searchName, from));



        return studentWeekViewTableView;
    }

    private static ObservableList<StudentWeekView> getStudentWeekViews(Teacher teacher, Classroom classroom, String searchName, LocalDate from) {
        ObservableList<StudentWeekView> studentWeekViewObservableList = FXCollections.observableArrayList();
        studentWeekViewObservableList.addAll(teacherService.getTeachersStudentWeekViews(teacher, classroom, searchName, from));
        return studentWeekViewObservableList;
    }
}
