package dao;

import model.DBObject;
import model.Teacher;
import model.exception.SchoolException;
import service.helpers.teacher.TeacherFormatHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DAOTeacher implements DAOService {
    public static String write(DBObject dbObject) throws SchoolException {
        Teacher teacher = (Teacher) dbObject;
        TeacherFormatHelper teacherHelper = new TeacherFormatHelper();
        String formattedTeacher = teacherHelper.format(teacher);
        File file = new File("database/teachers/" + teacher.getPersonalId() + ".txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(formattedTeacher);
        }
        catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }

        return "Succesfully writed teacher to " + file.getPath();
    }

    private static DBObject read(String src) throws SchoolException {
        Teacher teacher = new Teacher();
        TeacherFormatHelper teacherHelper = new TeacherFormatHelper();
        File teacherFile = new File("database/teachers/" + src);

        try(BufferedReader br = new BufferedReader(new FileReader(teacherFile))) {
            String strCurrentLine;
            StringBuilder teacherStringFromFile = new StringBuilder();
            while ((strCurrentLine = br.readLine()) != null) {
                teacherStringFromFile.append(strCurrentLine).append(System.lineSeparator());
            }

            teacher = teacherHelper.parse(teacherStringFromFile.toString());
        }
        catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }

        return teacher;
    }

    public static List<Teacher> readAll() throws SchoolException {
        List<Teacher> allTeachers = new ArrayList<>();
        File folder = new File("database/teachers/");
        File[] arrayOfFiles = folder.listFiles();

        if  (arrayOfFiles != null) {
            List<File> listOfFiles = new ArrayList<>(Arrays.asList(arrayOfFiles));
            List<String> namesOfFiles = new ArrayList<>();
            listOfFiles.forEach((f) -> namesOfFiles.add(f.getName()));
            for (String nameOfFile : namesOfFiles) {
                Teacher teacher = (Teacher) read(nameOfFile);
                if (teacher.isActive()) {
                    allTeachers.add(teacher);
                }
            }
            return allTeachers;
        }
        else {
            throw new SchoolException("No Teachers saved :(");
        }


    }



}

