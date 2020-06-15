package gui.director.manage.subjects;

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
import model.subject.Subject;
import model.exception.SchoolException;
import service.services.subject.SubjectService;
import service.services.subject.SubjectServiceImpl;

import static gui.common.AlertUtil.alert;

public class AddEditSubjectStage extends Stage {

    private static final int WIDTH = 500;

    private static final int HEIGHT = 200;

    private static final SubjectService subjectService = new SubjectServiceImpl();

    private Subject subject = new Subject();

    public AddEditSubjectStage(Stage owner, TableView<Subject> table, ModalMode modalMode) {
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);

        initScene(table, modalMode);
    }

    private void initScene(TableView<Subject> table, ModalMode modalMode) {
        GridPane grid = GridUtil.initGrid();
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(GraphicUserInterface.class.getResource("static/css/login.css").toExternalForm());
        fillGrid(grid, table, modalMode);
        this.setScene(scene);
    }

    private void fillGrid(GridPane grid,TableView<Subject> table, ModalMode modalMode) {
        Text sceneTitle = new Text("To add subject enter next information");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
        sceneTitle.setFill(Color.web(Colors.TEXT.toString()));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label nameLabel = LabelUtil.getLabel("Name: ");
        grid.add(nameLabel, 0, 1);

        TextField name = TextFieldUtil.getTextField();
        grid.add(name, 1, 1);

        Label costLabel = LabelUtil.getLabel("Cost per hour ($): ");
        grid.add(costLabel, 0, 2);

        TextField cost = TextFieldUtil.getTextField();
        grid.add(cost, 1, 2);

        Button button = new Button("Add");
        button.setStyle("-fx-background-color: #3b3b3b; -fx-text-fill: #9b9b9b;");

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(button);
        grid.add(hbBtn, 1, 3);

        if (modalMode == ModalMode.EDIT) {
            sceneTitle.setText("Edit subject:");
            subject = table.getSelectionModel().getSelectedItem();
            name.setText(subject.getName());
            name.setDisable(true);
            cost.setText("" + subject.getCostPerHour());
            button.setText("Edit");
        }

        button.setOnAction(click -> {
            if (modalMode == ModalMode.ADD) {
                subject.setCostPerHour(Integer.parseInt(cost.getText()));
                subject.setName(name.getText());
                try {
                    subjectService.addSubject(subject);
                    table.getItems().setAll(subjectService.getSubjects());
                } catch (SchoolException e) {
                    alert("Unexpected exception", "Can't add subject", e.getMessage());
                }
                this.close();
            }
            else if (modalMode == ModalMode.EDIT) {
                subject.setCostPerHour(Integer.parseInt(cost.getText()));
                subject.setName(name.getText());
                try {
                    subjectService.editSubject(subject);
                    table.getItems().setAll(subjectService.getSubjects());
                } catch (SchoolException e) {
                    alert("Unexpected exception","Can't edit subject",e.getMessage());
                }
                close();
            }
        });
    }
}