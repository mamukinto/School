package dao;

import model.exception.SchoolException;
import model.user.teacher.Teacher;
import service.helpers.teacher.TeacherFormatHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DAOTeacher implements DAOService<Teacher> {

    @Override
    public String write(Teacher teacher) throws SchoolException {
        TeacherFormatHelper teacherHelper = new TeacherFormatHelper();
        String formattedTeacher = teacherHelper.format(teacher);
        File file = new File("database/teachers/" + teacher.getPersonalId() + ".txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(formattedTeacher);
        } catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }

        return "Successfully writed teacher to " + file.getPath();
    }

    @Override
    public Teacher read(String src) throws SchoolException {
        Teacher teacher;
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

    @Override
    public List<Teacher> readAll() throws SchoolException {
        List<Teacher> allTeachers = new ArrayList<>();
        File folder = new File("database/teachers/");
        File[] arrayOfFiles = folder.listFiles();

        if  (arrayOfFiles != null) {
            List<File> listOfFiles = new ArrayList<>(Arrays.asList(arrayOfFiles));
            List<String> namesOfFiles = new ArrayList<>();
            listOfFiles.forEach((f) -> namesOfFiles.add(f.getName()));
            for (String nameOfFile : namesOfFiles) {
                Teacher teacher = read(nameOfFile);
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

