//package gui.director.manage.teachers;
//
//import gui.GraphicUserInterface;
//import gui.common.*;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import model.Student;
//import model.Teacher;
//import model.exception.SchoolException;
//import service.services.classroom.ClassroomsService;
//import service.services.classroom.ClassroomsServiceImpl;
//import service.services.student.StudentService;
//import service.services.student.StudentServiceImpl;
//import service.services.subject.SubjectService;
//import service.services.subject.SubjectServiceImpl;
//import service.services.teacher.TeacherService;
//import service.services.teacher.TeacherServiceImpl;
//
//import static gui.common.AlertUtil.alert;
//
//public class AddEditTeacherStage extends Stage {
//
//    private static final int WIDTH = 500;
//
//    private static final int HEIGHT = 450;
//
//    private static final ClassroomsService classroomsService = new ClassroomsServiceImpl();
//
//    private static final SubjectService subjectService = new SubjectServiceImpl();
//
//    private static final TeacherService teacherService = new TeacherServiceImpl();
//
//
//    private Student student = new Student();
//
//    public AddEditTeacherStage(Stage owner, TableView<Teacher> table, ModalMode modalMode) {
//        this.setWidth(WIDTH);
//        this.setHeight(HEIGHT);
//        this.initOwner(owner);
//        this.initModality(Modality.WINDOW_MODAL);
//
//        initScene(table, modalMode);
//    }
//
//
//    private void initScene(TableView<Teacher> table, ModalMode modalMode) {
//        GridPane grid = GridUtil.initGrid();
//        Scene scene = new Scene(grid);
//        scene.getStylesheets().add(GraphicUserInterface.class.getResource("static/css/login.css").toExternalForm());
//        fillGrid(grid, table, modalMode);
//        this.setScene(scene);
//    }
//
//
//
//    private void fillGrid(GridPane grid,TableView<Teacher> table, ModalMode modalMode) {
//        Text scenetitle = new Text("To add teacher enter next information");
//        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
//        scenetitle.setFill(Color.web(Colors.TEXT.toString()));
//        grid.add(scenetitle, 0, 0, 2, 1);
//
//
//
//        Label firstNameLabel = LabelUtil.getLabel("First name: ");
//        grid.add(firstNameLabel, 0, 1);
//
//        TextField firstName = TextFieldUtil.getTextField();
//        grid.add(firstName, 1, 1);
//
//        Label lastNameLabel = LabelUtil.getLabel("Last name: ");
//        grid.add(lastNameLabel, 0, 2);
//
//        TextField lastName = TextFieldUtil.getTextField();
//        grid.add(lastName, 1, 2);
//
//        Label personalIdLabel = LabelUtil.getLabel("Personal id: ");
//        grid.add(personalIdLabel, 0, 3);
//
//        TextField personalId = TextFieldUtil.getTextField();
//        grid.add(personalId, 1, 3);
//
//        Label emailLabel = LabelUtil.getLabel("Email: ");
//        grid.add(emailLabel, 0, 4);
//
//        TextField email = TextFieldUtil.getTextField();
//        grid.add(email, 1, 4);
//
//        Label classroomLabel = LabelUtil.getLabel("Classroom ");
//        grid.add(classroomLabel, 0, 5);
//
//        ComboBox<String> comboBox = new ComboBox<>();
//        comboBox.setStyle("-fx-background-color: " + Colors.SECONDARY + "; -fx-mark-color: " + Colors.SECONDARY + ";");
//
//        comboBox.setPromptText("Click to select");
//        comboBox.setEditable(false);
//
//
//        updateClassroomsToChoiceBox(comboBox);
//
//        grid.add(comboBox, 1,5);
//
//        Button button = new Button("Add");
//        button.setStyle("-fx-background-color: " + Colors.SECONDARY + ";");
//        button.setTextFill(Color.web(Colors.TEXT.toString()));
//
//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(button);
//        grid.add(hbBtn, 1, 6);
//
//        if (modalMode == ModalMode.EDIT) {
//            scenetitle.setText("Edit student:");
//            student = table.getSelectionModel().getSelectedItem();
//            firstName.setText(student.getFirstName());
//            lastName.setText(student.getLastName());
//            personalId.setText(student.getPersonalId());
//            personalId.setDisable(true);
//            email.setText(student.getEmail());
//            comboBox.setValue(student.getClassroom().getName());
//            button.setText("Edit");
//        }
//
//
//
//
//        button.setOnAction(click -> {
//            if (modalMode == ModalMode.ADD) {
//                student.setFirstName(firstName.getText());
//                student.setLastName(lastName.getText());
//                student.setPersonalId(personalId.getText());
//                student.setEmail(email.getText());
//                student.setClassroom(classroomsService.getClassroomByName(comboBox.getValue()));
//                try {
//                    studentService.addStudent(student);
//                } catch (SchoolException e) {
//                    alert("Unexpected exception", "Can't add student", e.getMessage());
//                }
//                this.close();
//            }
//            else if (modalMode == ModalMode.EDIT) {
//                student.setFirstName(firstName.getText());
//                student.setLastName(lastName.getText());
//                student.setEmail(email.getText());
//                student.setClassroom(classroomsService.getClassroomByName(comboBox.getValue()));
//                close();
//                try {
//                    studentService.editStudent(student);
//                } catch (SchoolException e) {
//                    alert("Unexpected exception","Can't edit student",e.getMessage());
//                }
//            }
//            table.getItems().setAll(studentService.getStudents());
//        });
//    }
//
//
//    private void updateClassroomsToChoiceBox(ComboBox<String> cb) {
//        classroomsService.getClassrooms().forEach(classroom -> cb.getItems().add(classroom.getName()));
//    }
//
//
//
//}