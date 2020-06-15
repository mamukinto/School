package gui.director.manage.classrooms;

import gui.GraphicUserInterface;
import gui.common.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.classrom.Classroom;
import model.subject.Subject;
import model.user.teacher.Teacher;
import model.exception.SchoolException;
import service.services.classroom.ClassroomsService;
import service.services.classroom.ClassroomsServiceImpl;
import service.services.subject.SubjectService;
import service.services.subject.SubjectServiceImpl;
import service.services.teacher.TeacherService;
import service.services.teacher.TeacherServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;

import static gui.common.AlertUtil.alert;

public class AddEditClassroomStage extends Stage {

    private static final int WIDTH = 500;

    private static final int HEIGHT = 400;

    private static final ClassroomsService classroomsService = new ClassroomsServiceImpl();

    private static final SubjectService subjectService = new SubjectServiceImpl();

    private static final TeacherService teacherService = new TeacherServiceImpl();

    private Classroom classroom = new Classroom();

    private HashMap<Subject, Teacher> teacherHashMap = new HashMap<>();

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


        subjectTeacherComboBoxes(grid);



        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(button);
        grid.add(hbBtn, 1, 4);

        if (modalMode == ModalMode.EDIT) {
            scenetitle.setText("Edit classroom:");
            classroom = table.getSelectionModel().getSelectedItem();
            id.setText("" + classroom.getId());
            id.setDisable(true);
            name.setText(classroom.getName());
            button.setText("Edit");
            teacherHashMap = classroom.getTeachers();
        }




        button.setOnAction(click -> {
            if (modalMode == ModalMode.ADD) {
                classroom.setId(Integer.parseInt(id.getText()));
                classroom.setName(name.getText());
                classroom.setTeachers(teacherHashMap);
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
                classroom.setTeachers(teacherHashMap);
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

    private void subjectTeacherComboBoxes(GridPane grid) {
        ComboBox<String> subjectComboBox = new ComboBox<>();
        subjectComboBox.setStyle("-fx-background-color: " + Colors.SECONDARY + "; -fx-mark-color: " + Colors.SECONDARY + ";");

        subjectComboBox.setPromptText("Select Subject");
        subjectComboBox.setEditable(false);

        subjectService.getSubjects().forEach(subject -> subjectComboBox.getItems().add(subject.getName()));

        grid.add(subjectComboBox,0,3);

        ComboBox<String> teacherComboBox = new ComboBox<>();
        teacherComboBox.setStyle("-fx-background-color: " + Colors.SECONDARY + "; -fx-mark-color: " + Colors.SECONDARY + ";");

        teacherComboBox.setPromptText("Select Teacher");
        teacherComboBox.setEditable(false);
        teacherComboBox.setDisable(true);

        subjectComboBox.setOnAction(click -> {
            if (!subjectComboBox.getValue().equals("Select Subject")) {
                if (teacherService.getTeachersBySubject(subjectComboBox.getValue()).size() != 0) {
                    teacherComboBox.setDisable(false);
                    teacherComboBox.getItems().setAll(new ArrayList<>());
                    teacherService.getTeachersBySubject(subjectComboBox.getValue()).forEach(teacher -> {
                        teacherComboBox.getItems().add(teacher.getPersonalId());
                        if (teacherHashMap.containsKey(subjectService.getSubjectByName(subjectComboBox.getValue()))) {
                            teacherComboBox.setValue(teacherHashMap.get(subjectService.getSubjectByName(subjectComboBox.getValue())).getPersonalId());
                        }
                    });
                }
            }
        });

        teacherComboBox.setOnAction(click -> {
            if (teacherComboBox.getValue() != null) {
                teacherHashMap.put(subjectService.getSubjectByName(subjectComboBox.getValue()), teacherService.getTeacherByPersonalId(teacherComboBox.getValue()));
            }
        });


        grid.add(teacherComboBox,1,3);
    }


}
