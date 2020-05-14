package gui.director.manage.classrooms;

import gui.GraphicUserInterface;
import gui.common.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Classroom;
import model.exception.SchoolException;
import service.services.classroom.ClassroomsService;
import service.services.classroom.ClassroomsServiceImpl;

import static gui.common.AlertUtil.alert;

public class AddEditClassroomStage extends Stage {

    private static final int WIDTH = 500;

    private static final int HEIGHT = 200;

    private static final ClassroomsService classroomsService = new ClassroomsServiceImpl();

    private Classroom classroom = new Classroom();

    public AddEditClassroomStage(Stage owner, TableView<Classroom> table, ModalMode modalMode) {
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);

        initScene(table, modalMode);
    }


    private void initScene(TableView<Classroom> table, ModalMode modalMode) {
        GridPane grid = GridUtil.initGrid();
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(GraphicUserInterface.class.getResource("static/css/login.css").toExternalForm());
        fillGrid(grid, table, modalMode);
        this.setScene(scene);
    }



    private void fillGrid(GridPane grid,TableView<Classroom> table, ModalMode modalMode) {
        Text scenetitle = new Text("To add classroom enter next information");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
        scenetitle.setFill(Color.web(Colors.TEXT.toString()));
        grid.add(scenetitle, 0, 0, 2, 1);



        Label idLabel = LabelUtil.getLabel("ID: ");
        grid.add(idLabel, 0, 1);

        TextField id = TextFieldUtil.getTextField();
        grid.add(id, 1, 1);

        Label nameLabel = LabelUtil.getLabel("Name: ");
        grid.add(nameLabel, 0, 2);

        TextField name = TextFieldUtil.getTextField();
        grid.add(name, 1, 2);

        Button button = new Button("Add");
        button.setStyle("-fx-background-color: #3b3b3b; -fx-text-fill: #9b9b9b;");

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(button);
        grid.add(hbBtn, 1, 3);

        if (modalMode == ModalMode.EDIT) {
            scenetitle.setText("Edit classroom:");
            classroom = table.getSelectionModel().getSelectedItem();
            id.setText("" + classroom.getId());
            id.setDisable(true);
            name.setText(classroom.getName());
            button.setText("Edit");
        }




        button.setOnAction(click -> {
            if (modalMode == ModalMode.ADD) {
                classroom.setId(Integer.parseInt(id.getText()));
                classroom.setName(name.getText());
                try {
                    classroomsService.addClassroom(classroom);
                    table.getItems().setAll(classroomsService.getClassrooms());
                } catch (SchoolException e) {
                    alert("Unexpected exception", "Can't add classroom", e.getMessage());
                }
                this.close();
            }
            else if (modalMode == ModalMode.EDIT) {
                classroom.setId(Integer.parseInt(id.getText()));
                classroom.setName(name.getText());
                try {
                    classroomsService.editClassroom(classroom);
                    table.getItems().setAll(classroomsService.getClassrooms());
                } catch (SchoolException e) {
                    alert("Unexpected exception","Can't edit classroom",e.getMessage());
                }
                close();
            }
        });
    }

}
