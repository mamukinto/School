package gui.director;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Student;
import service.services.student.StudentService;
import service.services.student.StudentServiceImpl;


public class StudentsTableGenerator {

    public static TableView<Student> getStudentTableView() {
        TableView<Student> studentTableView = new TableView<>();
        studentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        studentTableView.setPlaceholder(new Label("No Students in this school yet."));
        TableColumn<Student, Long> idColumn = new TableColumn<>("ID");
        idColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("personalId"));
        idColumn.prefWidthProperty().bind(studentTableView.widthProperty().multiply(0.1));

        TableColumn<Student, String> firstNameColumn = new TableColumn<>("Name");
        firstNameColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.prefWidthProperty().bind(studentTableView.widthProperty().multiply(0.3));

        TableColumn<Student, String> lastNameColumn = new TableColumn<>("Last name");
        lastNameColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.prefWidthProperty().bind(studentTableView.widthProperty().multiply(0.3));

        TableColumn<Student, String> classroomColumn = new TableColumn<>("Classroom");
        classroomColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        classroomColumn.setCellValueFactory(new PropertyValueFactory<>("classroom"));
        classroomColumn.prefWidthProperty().bind(studentTableView.widthProperty().multiply(0.3));

        studentTableView.getColumns().add(idColumn);
        studentTableView.getColumns().add(firstNameColumn);
        studentTableView.getColumns().add(lastNameColumn);
        studentTableView.getColumns().add(classroomColumn);

        studentTableView.setItems(getStudents());


        return studentTableView;
    }

    private static ObservableList<Student> getStudents() {
        StudentService studentService = new StudentServiceImpl();

        ObservableList<Student> students = FXCollections.observableArrayList();
        students.addAll(studentService.getStudents());
        return students;
    }
}
