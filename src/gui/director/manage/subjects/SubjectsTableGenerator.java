package gui.director.manage.subjects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Subject;
import service.services.subject.SubjectService;
import service.services.subject.SubjectServiceImpl;

public class SubjectsTableGenerator {
    public static TableView<Subject> getTableView() {
        TableView<Subject> classroomTableView = new TableView<>();
        classroomTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        classroomTableView.setPlaceholder(new Label("No Subjects in this school yet."));

        TableColumn<Subject, Long> nameColumn = new TableColumn<>("Name");
        nameColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.prefWidthProperty().bind(classroomTableView.widthProperty().multiply(0.5));

        TableColumn<Subject, Long> costPerHourColumn = new TableColumn<>("Cost per hour ($)");
        costPerHourColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        costPerHourColumn.setCellValueFactory(new PropertyValueFactory<>("costPerHour"));
        costPerHourColumn.prefWidthProperty().bind(classroomTableView.widthProperty().multiply(0.5));


        classroomTableView.getColumns().add(nameColumn);
        classroomTableView.getColumns().add(costPerHourColumn);

        classroomTableView.setItems(getSubjects());


        return classroomTableView;
    }

    private static ObservableList<Subject> getSubjects() {
        SubjectService subjectService = new SubjectServiceImpl();

        ObservableList<Subject> subjects = FXCollections.observableArrayList();
        subjects.addAll(subjectService.getSubjects());
        return subjects;
    }
}
