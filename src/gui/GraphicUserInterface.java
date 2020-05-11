package gui;

import gui.common.Colors;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.exception.SchoolException;
import service.Startup;
import service.services.director.DirectorService;
import service.services.director.DirectorServiceImpl;

public class GraphicUserInterface extends Application {
    private static Scene scene = new Scene(new Parent() {
        @Override
        protected ObservableList<Node> getChildren() {
            return super.getChildren();
        }
    });

    private static DirectorService directorService = new DirectorServiceImpl();

    @Override
    public void start(Stage stage) {
        Startup.startUp();
        Login.login(scene,stage);
        Startup.checkDirector(scene,stage);


        stage.setTitle("School Management System v1.0");

        stage.setScene(scene);

        stage.show();
    }


    public static void registerDirector(Scene scene,Stage stage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 50, 50, 50));
        grid.setStyle("-fx-background-color: " + Colors.MAIN + ";");
        grid.setPrefSize(600, 400);

        Text scenetitle = new Text("Enter Director id and password to register");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setFill(Color.web(Colors.TEXT.toString()));
        grid.add(scenetitle, 0, 0, 2, 1);


        Label userName = new Label("Personal ID:");
        userName.setTextFill(Color.web(Colors.TEXT.toString()));
        userName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(userName, 0, 2);

        TextField userTextField = new TextField();
        userTextField.setStyle("-fx-background-color: " + Colors.SECONDARY + "; -fx-text-inner-color: " + Colors.TEXT + ";");
        userTextField.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(userTextField, 1, 2);

        Label pw = new Label("Password:");
        pw.setTextFill(Color.web(Colors.TEXT.toString()));
        pw.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(pw, 0, 3);

        PasswordField pwBox = new PasswordField();
        pwBox.setStyle("-fx-background-color: " + Colors.SECONDARY + "; -fx-text-inner-color: " + Colors.TEXT + ";");
        grid.add(pwBox, 1, 3);

        Button btn = new Button("Sign up");
        btn.setStyle("-fx-background-color: " + Colors.SECONDARY + ";");
        btn.setTextFill(Color.web(Colors.TEXT.toString()));

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        btn.setOnAction(click -> {
            try {
                directorService.addDirector(userTextField.getText(),pwBox.getText());
                Login.login(scene,stage);
            } catch (SchoolException e) {
                alert("Unexpected exception","!",e.getMessage());
            }
        });


        scene.setRoot(grid);
    }


    static private void alert(String title,String header,String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);

        alert.showAndWait();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
