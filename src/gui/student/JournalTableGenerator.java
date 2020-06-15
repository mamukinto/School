package gui.student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.user.student.Student;
import model.user.student.SubjectWeekViewForStudent;
import service.services.student.StudentService;
import service.services.student.StudentServiceImpl;

import java.time.LocalDate;

public class JournalTableGenerator {

    private static StudentService studentService = new StudentServiceImpl();

    public static TableView<SubjectWeekViewForStudent> getTableView(Student student, LocalDate from, Stage stage) {
        TableView<SubjectWeekViewForStudent> journalTableView = new TableView<>();
        journalTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        journalTableView.setPlaceholder(new Label("No Subjects for this student yet."));

        TableColumn<SubjectWeekViewForStudent, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        nameColumn.prefWidthProperty().bind(journalTableView.widthProperty().multiply(0.25));

        TableColumn<SubjectWeekViewForStudent, String> mondayColumn = new TableColumn<>("Monday");
        mondayColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        mondayColumn.setCellValueFactory(new PropertyValueFactory<>("mondayMark"));
        mondayColumn.prefWidthProperty().bind(journalTableView.widthProperty().multiply(0.15));

        TableColumn<SubjectWeekViewForStudent, String> tuesdayColumn = new TableColumn<>("Tuesday");
        tuesdayColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        tuesdayColumn.setCellValueFactory(new PropertyValueFactory<>("tuesdayMark"));
        tuesdayColumn.prefWidthProperty().bind(journalTableView.widthProperty().multiply(0.15));

        TableColumn<SubjectWeekViewForStudent, String> wednesdayColumn = new TableColumn<>("Wednesday");
        wednesdayColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        wednesdayColumn.setCellValueFactory(new PropertyValueFactory<>("wednesdayMark"));
        wednesdayColumn.prefWidthProperty().bind(journalTableView.widthProperty().multiply(0.15));

        TableColumn<SubjectWeekViewForStudent, String> thursdayColumn = new TableColumn<>("Thursday");
        thursdayColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        thursdayColumn.setCellValueFactory(new PropertyValueFactory<>("thursdayMark"));
        thursdayColumn.prefWidthProperty().bind(journalTableView.widthProperty().multiply(0.15));

        TableColumn<SubjectWeekViewForStudent, String> fridayColumn = new TableColumn<>("Friday");
        fridayColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        fridayColumn.setCellValueFactory(new PropertyValueFactory<>("fridayMark"));
        fridayColumn.prefWidthProperty().bind(journalTableView.widthProperty().multiply(0.15));

        journalTableView.getColumns().add(nameColumn);
        journalTableView.getColumns().add(mondayColumn);
        journalTableView.getColumns().add(tuesdayColumn);
        journalTableView.getColumns().add(wednesdayColumn);
        journalTableView.getColumns().add(thursdayColumn);
        journalTableView.getColumns().add(fridayColumn);


        journalTableView.getItems().addAll(getSubjectWeekViewForStudentList(student, from));

        return journalTableView;
    }

    private static ObservableList<SubjectWeekViewForStudent> getSubjectWeekViewForStudentList(Student student,LocalDate from) {
        ObservableList<SubjectWeekViewForStudent> swvfs = FXCollections.observableArrayList();
        swvfs.addAll(studentService.getSubjectWeekViewForStudent(student, from));
        return swvfs;
    }
}
