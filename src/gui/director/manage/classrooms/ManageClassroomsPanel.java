package gui.director.manage.classrooms;

import gui.GraphicUserInterface;
import gui.common.AlertUtil;
import gui.common.Colors;
import gui.common.ModalMode;
import gui.common.StyleButton;
import gui.director.DirectorPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.classrom.Classroom;
import model.exception.SchoolException;
import service.services.classroom.ClassroomsService;
import service.services.classroom.ClassroomsServiceImpl;

public class ManageClassroomsPanel {

    private static final ClassroomsService classroomsService = new ClassroomsServiceImpl();

    public static void show(Scene scene, Stage stage) {
        TableView<Classroom> table = ClassroomsTableGenerator.getTableView();
        scene.getStylesheets().add(GraphicUserInterface.class.getResource("static/css/tableStyle.css").toExternalForm());

        BorderPane root = new BorderPane();
        root.setCenter(table);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(40);
        vBox.setPadding(new Insets(0,50,0,50));
        vBox.setStyle("-fx-background-color:" + Colors.MAIN + ";");

        Button back = new Button("Back");
        StyleButton.style(back);
        Button b1 = new Button("Add");
        b1.setPrefSize(150,50);
        StyleButton.style(b1);
        Button b2 = new Button("Remove");
        b2.setPrefSize(150,50);
        StyleButton.style(b2);
        Button b3 = new Button("Edit");
        b3.setPrefSize(150,50);
        StyleButton.style(b3);


        vBox.getChildren().add(back);
        vBox.getChildren().add(b1);
        vBox.getChildren().add(b2);
        vBox.getChildren().add(b3);


        back.setOnAction(click -> DirectorPanel.show(scene,stage));
        b1.setOnAction(click -> {
            AddEditClassroomStage addEditClassroomStage = new AddEditClassroomStage(stage,table, ModalMode.ADD);
            addEditClassroomStage.show();
        });
        b2.setOnAction(click -> removeClassroom(table));
        b3.setOnAction(click -> {
            AddEditClassroomStage addEditClassroomStage = new AddEditClassroomStage(stage,table, ModalMode.EDIT);
            addEditClassroomStage.show();
        });


        root.setRight(vBox);
        scene.setRoot(root);
    }

    private static void removeClassroom(TableView<Classroom> tableView) {
        try {
            classroomsService.removeClassroom(tableView.getSelectionModel().getSelectedItem());
            tableView.getItems().setAll(classroomsService.getClassrooms());
            System.out.println(tableView.getItems().toArray().toString());
        } catch (SchoolException e) {
            AlertUtil.alert("Exception","Can't remove Classroom",e.getMessage());
        }
    }
}
