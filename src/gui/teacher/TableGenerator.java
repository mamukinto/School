package gui.teacher;

import gui.common.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import model.*;
import model.exception.SchoolException;
import model.user.student.Student;
import model.user.student.StudentWeekView;
import model.user.teacher.Teacher;
import service.services.event.EventService;
import service.services.event.EventServiceImpl;
import service.services.mark.MarkService;
import service.services.mark.MarkServiceImpl;
import service.services.student.StudentService;
import service.services.student.StudentServiceImpl;
import service.services.teacher.TeacherService;
import service.services.teacher.TeacherServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TableGenerator {

    private final static TeacherService teacherService = new TeacherServiceImpl();

    private final static StudentService studentService = new StudentServiceImpl();

    private final static MarkService markService = new MarkServiceImpl();

    private final static EventService eventService = new EventServiceImpl();

    public static TableView<StudentWeekView> getTableView(Teacher teacher, Classroom classroom, LocalDate from, Stage stage) {
        TableView<StudentWeekView> studentWeekViewTableView = new TableView<>();
        studentWeekViewTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        studentWeekViewTableView.setPlaceholder(new Label("No Students in this classroom yet."));
        studentWeekViewTableView.getSelectionModel().setCellSelectionEnabled(true);
        studentWeekViewTableView.setEditable(true);


        TableColumn<StudentWeekView, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.prefWidthProperty().bind(studentWeekViewTableView.widthProperty().multiply(0.25));
        nameColumn.setOnEditStart(t -> {
            try {
                Student student = studentService.getStudentById(studentWeekViewTableView.getSelectionModel().getSelectedItem().getPersonalId());
                markService.updateJournal(student);
                StudentInfoModal.show(stage, student, teacher);
            } catch (SchoolException e) {
                AlertUtil.alert("Unexpected Exception", "Can't find this student" , e.getMessage());
            }
        } );

        TableColumn<StudentWeekView, String> mondayColumn = new TableColumn<>("Monday" + "(" + from + ")");
        mondayColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        mondayColumn.setCellValueFactory(new PropertyValueFactory<>("mondayMark"));
        mondayColumn.prefWidthProperty().bind(studentWeekViewTableView.widthProperty().multiply(0.15));
        mondayColumn.setEditable(true);
        mondayColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        mondayColumn.setOnEditCommit(t -> {
            studentWeekViewTableView.getSelectionModel().getSelectedItem().setMondayMark(t.getNewValue());
            try {
                createAndAddEvent(studentService.getStudentById(studentWeekViewTableView.getSelectionModel().getSelectedItem().getPersonalId()), teacher, t.getNewValue() );
                teacherService.addMarkToStudent(teacher,  new Mark(Integer.parseInt(t.getNewValue())), studentService.getStudentById(studentWeekViewTableView.getSelectionModel().getSelectedItem().getPersonalId()), from);
            } catch (SchoolException e) {
                AlertUtil.alert("Unexpected Exception", " Can't update student marks", e.getMessage());
            }
        });


        TableColumn<StudentWeekView, String> tuesdayColumn = new TableColumn<>("Tuesday" + "(" + from.plusDays(1) + ")");
        tuesdayColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        tuesdayColumn.setCellValueFactory(new PropertyValueFactory<>("tuesdayMark"));
        tuesdayColumn.prefWidthProperty().bind(studentWeekViewTableView.widthProperty().multiply(0.15));
        tuesdayColumn.setEditable(true);
        tuesdayColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        tuesdayColumn.setOnEditCommit(t -> {
            studentWeekViewTableView.getSelectionModel().getSelectedItem().setTuesdayMark(t.getNewValue());
            try {
                createAndAddEvent(studentService.getStudentById(studentWeekViewTableView.getSelectionModel().getSelectedItem().getPersonalId()), teacher, t.getNewValue() );
                teacherService.addMarkToStudent(teacher,  new Mark(Integer.parseInt(t.getNewValue())), studentService.getStudentById(studentWeekViewTableView.getSelectionModel().getSelectedItem().getPersonalId()), from.plusDays(1));
            } catch (SchoolException e) {
                AlertUtil.alert("Unexpected Exception", " Can't update student marks", e.getMessage());
            }
        });

        TableColumn<StudentWeekView, String> wednesdayColumn = new TableColumn<>("Wednesday" + "(" + from.plusDays(2) + ")");
        wednesdayColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        wednesdayColumn.setCellValueFactory(new PropertyValueFactory<>("wednesdayMark"));
        wednesdayColumn.prefWidthProperty().bind(studentWeekViewTableView.widthProperty().multiply(0.15));
        wednesdayColumn.setEditable(true);
        wednesdayColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        wednesdayColumn.setOnEditCommit(t -> {
            studentWeekViewTableView.getSelectionModel().getSelectedItem().setWednesdayMark(t.getNewValue());
            try {
                createAndAddEvent(studentService.getStudentById(studentWeekViewTableView.getSelectionModel().getSelectedItem().getPersonalId()), teacher, t.getNewValue() );
                teacherService.addMarkToStudent(teacher,  new Mark(Integer.parseInt(t.getNewValue())), studentService.getStudentById(studentWeekViewTableView.getSelectionModel().getSelectedItem().getPersonalId()), from.plusDays(2));
            } catch (SchoolException e) {
                AlertUtil.alert("Unexpected Exception", " Can't update student marks", e.getMessage());
            }
        });


        TableColumn<StudentWeekView, String> thursdayColumn = new TableColumn<>("Thursday" + "(" + from.plusDays(3) + ")");
        thursdayColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        thursdayColumn.setCellValueFactory(new PropertyValueFactory<>("thursdayMark"));
        thursdayColumn.prefWidthProperty().bind(studentWeekViewTableView.widthProperty().multiply(0.15));
        thursdayColumn.setEditable(true);
        thursdayColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        thursdayColumn.setOnEditCommit(t -> {
            studentWeekViewTableView.getSelectionModel().getSelectedItem().setThursdayMark(t.getNewValue());
            try {
                createAndAddEvent(studentService.getStudentById(studentWeekViewTableView.getSelectionModel().getSelectedItem().getPersonalId()), teacher, t.getNewValue() );
                teacherService.addMarkToStudent(teacher,  new Mark(Integer.parseInt(t.getNewValue())), studentService.getStudentById(studentWeekViewTableView.getSelectionModel().getSelectedItem().getPersonalId()), from.plusDays(3));
            } catch (SchoolException e) {
                AlertUtil.alert("Unexpected Exception", " Can't update student marks", e.getMessage());
            }
        });

        TableColumn<StudentWeekView, String> fridayColumn = new TableColumn<>("Friday" + "(" + from.plusDays(4) + ")");
        fridayColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        fridayColumn.setCellValueFactory(new PropertyValueFactory<>("fridayMark"));
        fridayColumn.prefWidthProperty().bind(studentWeekViewTableView.widthProperty().multiply(0.15));
        fridayColumn.setEditable(true);
        fridayColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fridayColumn.setOnEditCommit(t -> {
            studentWeekViewTableView.getSelectionModel().getSelectedItem().setFridayMark(t.getNewValue());
            try {
                createAndAddEvent(studentService.getStudentById(studentWeekViewTableView.getSelectionModel().getSelectedItem().getPersonalId()), teacher, t.getNewValue() );
                teacherService.addMarkToStudent(teacher,  new Mark(Integer.parseInt(t.getNewValue())), studentService.getStudentById(studentWeekViewTableView.getSelectionModel().getSelectedItem().getPersonalId()), from.plusDays(4));
            } catch (SchoolException e) {
                AlertUtil.alert("Unexpected Exception", " Can't update student marks", e.getMessage());
            }
        });

        studentWeekViewTableView.getColumns().add(nameColumn);
        studentWeekViewTableView.getColumns().add(mondayColumn);
        studentWeekViewTableView.getColumns().add(tuesdayColumn);
        studentWeekViewTableView.getColumns().add(wednesdayColumn);
        studentWeekViewTableView.getColumns().add(thursdayColumn);
        studentWeekViewTableView.getColumns().add(fridayColumn);


        studentWeekViewTableView.setItems(getStudentWeekViews(teacher, classroom, from));



        return studentWeekViewTableView;
    }

    private static ObservableList<StudentWeekView> getStudentWeekViews(Teacher teacher, Classroom classroom, LocalDate from) {
        ObservableList<StudentWeekView> studentWeekViewObservableList = FXCollections.observableArrayList();
        studentWeekViewObservableList.addAll(teacherService.getTeachersStudentWeekViews(teacher, classroom, from));
        return studentWeekViewObservableList;
    }

    private static void createAndAddEvent(Student student, Teacher teacher, String markValue) {
        Event event = new Event();
        event.setContent(teacher.getFirstName() + " " + teacher.getLastName() + " wrote mark to you (" + markValue + ")");
        event.setDate(LocalDateTime.now());
        event.setStudentPersonalId(student.getPersonalId());
        try {
            eventService.addEvent(event,student);
        } catch (SchoolException e) {
            AlertUtil.alert("Unexpected Exception","Can't add event",e.getMessage());
        }
    }

}
