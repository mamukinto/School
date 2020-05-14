package gui.director.manage.teachers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Teacher;
import service.services.teacher.TeacherService;
import service.services.teacher.TeacherServiceImpl;

public class TeachersTableGenerator{

    public static TableView<Teacher> getTableView() {
        TableView<Teacher> teacherTableView = new TableView<>();
        teacherTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        teacherTableView.setPlaceholder(new Label("No Students in this school yet."));
        TableColumn<Teacher, Long> idColumn = new TableColumn<>("ID");
        idColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("personalId"));
        idColumn.prefWidthProperty().bind(teacherTableView.widthProperty().multiply(0.1));

        TableColumn<Teacher, String> firstNameColumn = new TableColumn<>("Name");
        firstNameColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.prefWidthProperty().bind(teacherTableView.widthProperty().multiply(0.3));

        TableColumn<Teacher, String> lastNameColumn = new TableColumn<>("Last name");
        lastNameColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.prefWidthProperty().bind(teacherTableView.widthProperty().multiply(0.3));

        TableColumn<Teacher, String> subjectColumn = new TableColumn<>("Subject");
        subjectColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        subjectColumn.prefWidthProperty().bind(teacherTableView.widthProperty().multiply(0.3));

        teacherTableView.getColumns().add(idColumn);
        teacherTableView.getColumns().add(firstNameColumn);
        teacherTableView.getColumns().add(lastNameColumn);
        teacherTableView.getColumns().add(subjectColumn);

        teacherTableView.setItems(getTeachers());


        return teacherTableView;
    }

    private static ObservableList<Teacher> getTeachers() {
        TeacherService teacherService = new TeacherServiceImpl();

        ObservableList<Teacher> teacher = FXCollections.observableArrayList();
        teacher.addAll(teacherService.getTeachers());
        return teacher;
    }
}
