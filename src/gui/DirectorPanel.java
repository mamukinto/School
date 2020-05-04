package gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Student;
import model.exception.SchoolException;
import service.services.auth.AuthService;
import service.services.auth.AuthServiceImpl;
import service.services.classroom.ClassroomsService;
import service.services.classroom.ClassroomsServiceImpl;
import service.services.mark.MarkService;
import service.services.mark.MarkServiceImpl;
import service.services.student.StudentService;
import service.services.student.StudentServiceImpl;
import service.services.subject.SubjectService;
import service.services.subject.SubjectServiceImpl;
import service.services.teacher.TeacherService;
import service.services.teacher.TeacherServiceImpl;

import java.util.Timer;
import java.util.TimerTask;



class DirectorPanel {
    static Timer timer = new Timer();

    private static final TeacherService teacherService = new TeacherServiceImpl();

    private static final ClassroomsService classroomsService = new ClassroomsServiceImpl();

    private static final SubjectService subjectService = new SubjectServiceImpl();

    private static final StudentService studentService = new StudentServiceImpl();

    private static final MarkService markService = new MarkServiceImpl();

    private static final AuthService authService = new AuthServiceImpl();



    static void directorPanel(Scene scene, Stage stage) {
        stage.setWidth(800);
        stage.setHeight(600);

        GridPane grid = new GridPane();
        grid.setPrefSize(800,600);
        grid.setStyle("-fx-background-color:" + Colors.MAIN + ";");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);


        Text welcome = new Text("Welcome to director panel");
        welcome.setFill(Color.web(Colors.TEXT.toString()));
        welcome.setFont(new Font(24));


        Button bt1 = new Button("Manage Students");
        Button bt2 = new Button("Manage Teachers");
        Button bt3 = new Button("Manage Classrooms");
        Button bt4 = new Button("Manage Subjects");
        Button back = new Button("Back");

        styleButton(bt1);
        styleButton(bt2);
        styleButton(bt3);
        styleButton(bt4);
        styleButton(back);
        back.setFont(new Font(15));



        bt1.setPrefSize(300,200);
        bt2.setPrefSize(300,200);
        bt3.setPrefSize(300,200);
        bt4.setPrefSize(300,200);



        grid.add(welcome,0,0,3,1);
        grid.add(back,3,0);
        grid.add(bt1,0,1,2,1);
        grid.add(bt2,2,1,2,1);
        grid.add(bt3,0,2,2,1);
        grid.add(bt4,2,2,2,1);
        GridPane.setHalignment(back, HPos.RIGHT);

        bt1.setOnAction(click -> {
            manageStudents(scene,stage);
        });

        back.setOnAction(click -> {
            Login.login(scene, stage);
        });


        scene.setRoot(grid);
    }

    private static Button styleButton(Button button) {
        button.setTextFill(Color.web(Colors.TEXT.toString()));
        button.setStyle("-fx-background-color:" + Colors.SECONDARY + ";");
        button.setFont(new Font(21));
        button.setOnMouseEntered(enter -> {
            button.setStyle("-fx-background-color:#5b5b5b;");
            TimerTask task = new TimerTask()
            {
                public void run()
                {
                    button.setStyle("-fx-background-color:" + Colors.HOVER + ";");
                }
            };
            timer.schedule(task,100);

        });
        button.setOnMouseExited(exit -> {
            button.setStyle("-fx-background-color:#5b5b5b;");
            TimerTask task = new TimerTask()
            {
                public void run()
                {
                    button.setStyle("-fx-background-color:" + Colors.SECONDARY + ";");
                }
            };
            timer.schedule(task,100);
        });
        button.setOnMousePressed(press -> {
            button.setStyle("-fx-background-color:" + Colors.MAIN + ";");
        });
        button.setOnMouseReleased(release -> {
            button.setStyle("-fx-background-color:" + Colors.SECONDARY + ";");
        });
        return button;
    }

    private static void manageStudents(Scene scene,Stage stage) {
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList();
        updateStudentsListViewItems(items);
        list.setStyle("-fx-font-size:22px;");
        scene.getStylesheets().add(DirectorPanel.class.getResource("lisStyles.css").toExternalForm());
        list.setPrefWidth(400);
        list.setPrefHeight(600);
        list.setItems(items);

        BorderPane root = new BorderPane();
        root.setCenter(list);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(40);
        vBox.setPadding(new Insets(0,50,0,50));
        vBox.setStyle("-fx-background-color:" + Colors.MAIN + ";");

        Button back = new Button("Back");
        styleButton(back);
        Button b1 = new Button("Add");
        b1.setPrefSize(150,50);
        styleButton(b1);
        Button b2 = new Button("Remove");
        b2.setPrefSize(150,50);
        styleButton(b2);
        Button b3 = new Button("Edit");
        b3.setPrefSize(150,50);
        styleButton(b3);


        vBox.getChildren().add(back);
        vBox.getChildren().add(b1);
        vBox.getChildren().add(b2);
        vBox.getChildren().add(b3);


        back.setOnAction(click -> {
            directorPanel(scene,stage);
        });
        b1.setOnAction(click -> {
            addStudent(stage,items);
        });
        b2.setOnAction(click -> {
            removeStudent(items,list);
        });


        root.setRight(vBox);
        scene.setRoot(root);
    }

    @SuppressWarnings("unchecked")
    private static void addStudent(Stage stage,ObservableList items) {
        Stage addStudentStage = new Stage();
        addStudentStage.setWidth(500);
        addStudentStage.setHeight(400);
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid,500,400);
        addStudentStage.initOwner(stage);
        addStudentStage.initModality(Modality.WINDOW_MODAL);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 50, 50, 50));
        grid.setStyle("-fx-background-color: " + Colors.MAIN + ";");
        grid.setPrefSize(500, 500);



        Text scenetitle = new Text("To add student enter next information");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
        scenetitle.setFill(Color.web(Colors.TEXT.toString()));
        grid.add(scenetitle, 0, 0, 2, 1);



        Label firstNameLabel = new Label("First name:");
        firstNameLabel.setTextFill(Color.web(Colors.TEXT.toString()));
        firstNameLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(firstNameLabel, 0, 1);

        TextField firstName = new TextField();
        firstName.setStyle("-fx-background-color: " + Colors.SECONDARY + "; -fx-text-inner-color: " + Colors.TEXT + ";");
        firstName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(firstName, 1, 1);


        Label lastNameLabel = new Label("Last name:");
        lastNameLabel.setTextFill(Color.web(Colors.TEXT.toString()));
        lastNameLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(lastNameLabel, 0, 2);

        TextField lastName = new TextField();
        lastName.setStyle("-fx-background-color: " + Colors.SECONDARY + "; -fx-text-inner-color: " + Colors.TEXT + ";");
        lastName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(lastName, 1, 2);


        Label personalIdLabel = new Label("Personal ID:");
        personalIdLabel.setTextFill(Color.web(Colors.TEXT.toString()));
        personalIdLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(personalIdLabel, 0, 3);

        TextField personalId = new TextField();
        personalId.setStyle("-fx-background-color: " + Colors.SECONDARY + "; -fx-text-inner-color: " + Colors.TEXT + ";");
        personalId.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(personalId, 1, 3);


        Label emailLabel = new Label("E-mail:");
        emailLabel.setTextFill(Color.web(Colors.TEXT.toString()));
        emailLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(emailLabel, 0, 4);

        TextField email = new TextField();
        email.setStyle("-fx-background-color: " + Colors.SECONDARY + "; -fx-text-inner-color: " + Colors.TEXT + ";");
        email.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(email, 1, 4);



        Label classroomLabel = new Label("Classroom:");
        classroomLabel.setTextFill(Color.web(Colors.TEXT.toString()));
        classroomLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(classroomLabel, 0, 5);



        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setStyle("-fx-background-color: " + Colors.SECONDARY + "; -fx-mark-color: " + Colors.SECONDARY + ";");

//        comboBox.setCellFactory(new Callback<>() {
//            public ListCell<String> call(ListView<String> param) {
//                return new ListCell<>() {
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        setText(item);
//                        setBackground(new Background(new BackgroundFill(Color.web(Colors.SECONDARY.toString()), CornerRadii.EMPTY, Insets.EMPTY)));
//                        setTextFill(Color.web(Colors.TEXT.toString()));
//                    }
//                };
//            }
//        });

        comboBox.setPromptText("Click to select");
        comboBox.setEditable(false);


//        comboBox.setButtonCell(new ListCell() {
//
//            @Override
//            protected void updateItem(Object item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty || item == null) {
//                    // styled like -fx-prompt-text-fill:
//                    setStyle("-fx-text-fill: " + Colors.TEXT + ";");
//                } else {
//                    setStyle("-fx-text-fill: " + Colors.TEXT + ";");
//                    setText(item.toString());
//                }
//            }
//
//        });

//        TextField classroom = new TextField();
//        classroom.setStyle("-fx-background-color: " + Colors.SECONDARY + "; -fx-text-inner-color: " + Colors.TEXT + ";");
//        classroom.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
//        grid.add(classroom, 1, 5);


        updateClassroomsToChoiceBox(comboBox);

        grid.add(comboBox, 1,5);

        Button button = new Button("Add");
        button.setStyle("-fx-background-color: " + Colors.SECONDARY + ";");
        button.setTextFill(Color.web(Colors.TEXT.toString()));

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(button);
        grid.add(hbBtn, 1, 6);



        button.setOnAction(click -> {
            Student student = new Student();
            student.setFirstName(firstName.getText());
            student.setLastName(lastName.getText());
            student.setPersonalId(personalId.getText());
            student.setEmail(email.getText());
            student.setClassroom(classroomsService.getClassroomByName(comboBox.getValue()));
            try {
                studentService.addStudent(student);
            } catch (SchoolException e) {
                alert("Unexpected exception","Can't add student",e.getMessage());
            }
            updateStudentsListViewItems(items);
            addStudentStage.close();
        });


        addStudentStage.setScene(scene);
        addStudentStage.show();
    }



    private static void removeStudent(ObservableList items,ListView listView) {
        try {
            studentService.removeStudent(studentService.getStudentById(listView.getSelectionModel().getSelectedItem().toString().split(" ")[2]));
        } catch (SchoolException e) {
            alert("Unexpected exception","Can't remove student",e.getMessage());
        }
        updateStudentsListViewItems(items);
    }



    private static ComboBox<String> updateClassroomsToChoiceBox(ComboBox<String> cb) {

            classroomsService.getClassrooms().forEach(classroom -> {
                cb.getItems().add(classroom.getName());
            });

            return cb;
    }
    
    
    private static void updateStudentsListViewItems(ObservableList<String> items) {
        ObservableList<String> items1 = FXCollections.observableArrayList();
        studentService.getStudents().forEach(student -> {
            items1.add(student.getFirstName() + " " + student.getLastName() + " " + student.getPersonalId());
        });
        items.setAll(items1);
    }


    static private void alert(String title,String header,String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);

        alert.showAndWait();
    }


}
