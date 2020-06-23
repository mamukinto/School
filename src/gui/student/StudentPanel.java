package gui.student;

import gui.GraphicUserInterface;
import gui.Login;
import gui.common.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.exception.SchoolException;
import model.user.student.Student;
import service.services.student.StudentService;
import service.services.student.StudentServiceImpl;
import service.services.subject.SubjectService;
import service.services.subject.SubjectServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class StudentPanel {

    private static StudentService studentService = new StudentServiceImpl();

    private static SubjectService subjectService = new SubjectServiceImpl();

    public static void studentPanel(Scene scene, Student student, Stage stage) {
        stage.setMaximized(true);
        scene.getStylesheets().add(GraphicUserInterface.class.getResource("static/css/studentPanel.css").toExternalForm());
        BorderPane root = new BorderPane();
        scene.setRoot(root);
        VBox main = new VBox();
        VBox rightPanel = new VBox();
        ScrollPane mainScrollPane = new ScrollPane();
        ScrollPane rightScrollPane = new ScrollPane();
        rightScrollPane.setContent(rightPanel);
        mainScrollPane.setContent(main);
        root.setCenter(mainScrollPane);
        root.setRight(rightScrollPane);
        rightPanel.setPrefWidth(stage.getWidth() * 0.3);

        mainScrollPane.setFitToHeight(true);
        mainScrollPane.setFitToWidth(true);
        rightScrollPane.setFitToWidth(true);
        rightScrollPane.setFitToHeight(true);

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Options");
        menuBar.getMenus().add(menu);
        MenuItem changePassword = new MenuItem("Change password");
        MenuItem viewMyInfo = new MenuItem("View my information");
        MenuItem logOut = new MenuItem("Log out");
        menu.getItems().addAll(changePassword,viewMyInfo,logOut);
        root.setTop(menuBar);
        Label sceneTitle = new Label("What's new?");
        sceneTitle.setPadding(new Insets(25,0,0,60));
        sceneTitle.setFont(new Font(32));
//        main.setAlignment(Pos.CENTER);
        sceneTitle.setTextFill(Color.web(Colors.TEXT.toString()));
        VBox feed = new VBox();
        feed.setSpacing(20);
        feed.setPadding(new Insets(20,100,20,20));
        main.getChildren().add(sceneTitle);
        main.getChildren().add(feed);




        List<EventContainer> feedEvents = new ArrayList<>();
        student.getEvents().forEach(event -> {
            feedEvents.add(new EventContainer(event));
        });
        feed.getChildren().addAll(feedEvents);

        Text rightPanelTitle = new Text(student.getFirstName() + " " + student.getLastName());
        rightPanelTitle.setFont(new Font(32));
        rightPanelTitle.setFill(Color.web(Colors.TEXT.toString()));
        rightPanel.setSpacing(50);
        rightPanel.getChildren().add(rightPanelTitle);
        rightPanel.setPadding(new Insets(20,20,20,20));
        rightPanel.setAlignment(Pos.TOP_CENTER);

        subjectService.getSubjects().forEach(subject -> {
            Label averageMarkText = new Label(subject.getName() + " average mark: " + studentService.getAverageMarkOfStudentBySubject(student,subject));
            averageMarkText.setFont(new Font(22));
            averageMarkText.setTextFill(Color.web(Colors.TEXT.toString()));
            VBox vBox = new VBox();
            vBox.getChildren().add(averageMarkText);
            Separator separator = new Separator();
            vBox.getChildren().add(separator);
            vBox.setAlignment(Pos.TOP_CENTER);
            rightPanel.getChildren().add(vBox);
        });

        Button journalButton = new Button("More");
        StyleButton.style(journalButton);
        rightPanel.getChildren().add(journalButton);


        journalButton.setOnAction(click -> {
            JournalPanel.journalPanel(student,stage,scene);
        });

        logOut.setOnAction(click -> {
            Login.login(scene, stage);
        });

        changePassword.setOnAction(click -> {
            changePassword(stage, student);
        });

    }


    private static void changePassword(Stage stage, Student student) {
        Stage modal = new Stage();
        modal.getIcons().add(new Image("file:icon.png"));
        modal.setWidth(500);
        modal.setHeight(300);
        modal.initOwner(stage);
        modal.initModality(Modality.WINDOW_MODAL);
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(GraphicUserInterface.class.getResource("static/css/login.css").toExternalForm());
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 50, 50, 50));
        grid.setStyle("-fx-background-color: " + Colors.MAIN + ";");
        grid.setPrefSize(500, 300);


        Label sceneTitle = new Label("Change password:");
        sceneTitle.setTextFill(Color.web(Colors.TEXT.toString()));
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 22));

        grid.add(sceneTitle,0,0,2,1);

        Label oldPasswordLabel = LabelUtil.getLabel("Old password:");
        TextField oldPassword = TextFieldUtil.getTextField();
        grid.add(oldPasswordLabel,0,1);
        grid.add(oldPassword,1,1);

        Label newPasswordLabel = LabelUtil.getLabel("New password:");
        TextField newPassword = TextFieldUtil.getTextField();
        grid.add(newPasswordLabel,0,2);
        grid.add(newPassword,1,2);

        Label newPasswordLabel2 = LabelUtil.getLabel("Repeat new password:");
        TextField newPassword2 = TextFieldUtil.getTextField();
        grid.add(newPasswordLabel2,0,3);
        grid.add(newPassword2,1,3);

        HBox buttonHBox = new HBox();
        Button submit = new Button("Submit");
        buttonHBox.getChildren().add(submit);
        buttonHBox.setAlignment(Pos.BASELINE_RIGHT);
        StyleButton.style(submit);
        submit.setPrefSize(60,20);
        submit.setFont(new Font(12));
        grid.add(buttonHBox,0,4,2,1);

        submit.setOnAction(click -> {
            if (Integer.parseInt(student.getPassword()) == oldPassword.getText().hashCode()) {
                if (newPassword.getText().equals(newPassword2.getText())) {
                    student.setPassword("" + newPassword.getText().hashCode());
                    try {
                        studentService.editStudent(student);
                    } catch (SchoolException e) {
                        AlertUtil.alert("Unexpected exception", "Cant edit teacher", e.getMessage());
                    }
                    modal.close();
                } else {
                    sceneTitle.setText("New passwords doesn't match");
                    sceneTitle.setTextFill(Color.web(Colors.WARNING_TEXT.toString()));
                }
            } else if (oldPassword.getText().isEmpty()){
                sceneTitle.setText("Enter old password");
                sceneTitle.setTextFill(Color.web(Colors.WARNING_TEXT.toString()));
            } else if (Integer.parseInt(student.getPassword()) != oldPassword.getText().hashCode()) {
                sceneTitle.setText("Old Password is incorrect");
                sceneTitle.setTextFill(Color.web(Colors.WARNING_TEXT.toString()));
            }
        });


        modal.setScene(scene);
        modal.show();
    }




}
