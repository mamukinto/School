package dao;

import model.DBObject;
import model.Student;
import model.exception.SchoolException;
import service.helpers.student.StudentFormatHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DAOStudent implements DAOService {

    public static String write(DBObject dbObject) throws SchoolException {
        Student student = (Student) dbObject;
        StudentFormatHelper studentHelper = new StudentFormatHelper();
        String formattedStudent = studentHelper.format(student);
        File file = new File("database/students/" + student.getPersonalId() + "/" +  student.getPersonalId() + ".txt");
        file.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(formattedStudent);
        }
        catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }

        return "Succesfully writed student to " + file.getPath();
    }

    private static DBObject read(String src) throws SchoolException {
        DBObject student = new Student();
        StudentFormatHelper studentHelper = new StudentFormatHelper();
        File studentFile = new File("database/students/" + src);

        try(BufferedReader br = new BufferedReader(new FileReader(studentFile))) {
            String strCurrentLine;
            StringBuilder studentStringFromFile = new StringBuilder();
            while ((strCurrentLine = br.readLine()) != null) {
                studentStringFromFile.append(strCurrentLine).append(System.lineSeparator());
            }

            student = studentHelper.parse(studentStringFromFile.toString());
        }
        catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }



        return student;
    }

    public static List<Student> readAll() throws SchoolException {
        List<Student> allStudents = new ArrayList<>();
        File folder = new File("database/students/");
        File[] arrayOfFiles = folder.listFiles();
        if  (arrayOfFiles != null) {
            List<File> listOfFiles = new ArrayList<>(Arrays.asList(arrayOfFiles));
            List<String> namesOfFiles = new ArrayList<>();
            listOfFiles.forEach((f) -> namesOfFiles.add(f.getName()));
            for (String nameOfFile : namesOfFiles) {
                Student student = (Student) read(nameOfFile + "/" + nameOfFile + ".txt");
                if (student.isActive()) {
                    allStudents.add(student);
                }
            }
            return allStudents;
        }
        else {
            throw new SchoolException("No students saved :(");
        }
    }

}
