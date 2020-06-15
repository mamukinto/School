package gui.director.manage.classrooms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.classrom.Classroom;
import service.services.classroom.ClassroomsService;
import service.services.classroom.ClassroomsServiceImpl;

public class ClassroomsTableGenerator {
    public static TableView<Classroom> getTableView() {
        TableView<Classroom> classroomTableView = new TableView<>();
        classroomTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        classroomTableView.setPlaceholder(new Label("No Classrooms in this school yet."));

        TableColumn<Classroom, Long> idColumn = new TableColumn<>("ID");
        idColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.prefWidthProperty().bind(classroomTableView.widthProperty().multiply(0.5));

        TableColumn<Classroom, Long> nameColumn = new TableColumn<>("Name");
        nameColumn.setStyle("-fx-font-size:15; -fx-alignment:CENTER;");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.prefWidthProperty().bind(classroomTableView.widthProperty().multiply(0.5));


        classroomTableView.getColumns().add(idColumn);
        classroomTableView.getColumns().add(nameColumn);

        classroomTableView.setItems(getClassrooms());


        return classroomTableView;
    }

    private static ObservableList<Classroom> getClassrooms() {
        ClassroomsService classroomsService = new ClassroomsServiceImpl();

        ObservableList<Classroom> classrooms = FXCollections.observableArrayList();
        classrooms.addAll(classroomsService.getClassrooms());
        return classrooms;
    }
}
