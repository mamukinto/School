package dao;

import model.exception.SchoolException;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class DAODirector {

    public static void write(String login,String password) throws SchoolException {
        File file = new File("database/director/Director.txt");
        String loginAndPassword = login + System.lineSeparator() + password.hashCode();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(loginAndPassword);
        }
        catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }
    }

    public static List<String> read() throws SchoolException {
        File file = new File("database/director/Director.txt");
        List<String> stringList;
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String strCurrentLine;
            StringBuilder classroomStringFromFile = new StringBuilder();
            while ((strCurrentLine = br.readLine()) != null) {
                classroomStringFromFile.append(strCurrentLine).append(System.lineSeparator());
            }

            stringList = (Arrays.asList(classroomStringFromFile.toString().split(System.lineSeparator())));
        }
        catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }

        return stringList;
    }
}
