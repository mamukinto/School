package service;

import dao.DAODirector;
import gui.GraphicUserInterface;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.exception.SchoolException;
import service.services.StorageService;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Startup {

    public static void startUp() {
        checkAndCreateDirectories();
        try {
            StorageService.updateStorage();
        } catch (SchoolException e) {
            System.out.println("Can't update storage" + e.getMessage());
        }
    }

    private static void checkAndCreateDirectories() {
        File database = new File("database");
        List<File> directories = new ArrayList<>(Arrays.asList(
                new File("database/classrooms"),
                new File("database/students"),
                new File("database/subjects"),
                new File("database/teachers"),
                new File("database/director")
        ));
        create(database, directories);
    }

    @SuppressWarnings("all")
    private static void create(File database, List<File> directories) {
        if (!database.isDirectory()) {
            database.mkdir();
            directories.forEach((directory) -> directory.mkdir());
        }
    }

    public static void checkDirector(Scene scene, Stage stage) {
        if (Objects.requireNonNull(new File("database/director").listFiles()).length == 0) {
            GraphicUserInterface.registerDirector(scene,stage);
        };
    }
}
