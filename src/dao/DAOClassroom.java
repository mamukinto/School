package dao;

import model.classrom.Classroom;
import model.exception.SchoolException;
import service.helpers.classroom.ClassroomFormatHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DAOClassroom implements DAOService<Classroom> {

    @Override
    public String write(Classroom classroom) throws SchoolException {
        ClassroomFormatHelper classroomHelper = new ClassroomFormatHelper();
        String formattedClassroom = classroomHelper.format(classroom);
        File file = new File("database/classrooms/" + classroom.getId() + ".txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(formattedClassroom);
        } catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }

        return "Succesfully writed classroom to " + file.getPath();
    }

    @Override
    public Classroom read(String src) throws SchoolException {
        Classroom classroom = new Classroom();
        ClassroomFormatHelper classroomHelper = new ClassroomFormatHelper();
        File classroomFile = new File("database/classrooms/" + src);

        try (BufferedReader br = new BufferedReader(new FileReader(classroomFile))) {
            String strCurrentLine;
            StringBuilder classroomStringFromFile = new StringBuilder();
            while ((strCurrentLine = br.readLine()) != null) {
                classroomStringFromFile.append(strCurrentLine).append(System.lineSeparator());
            }

            classroom = ((Classroom) classroomHelper.parse(classroomStringFromFile.toString()));
        } catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }

        return classroom;
    }

    @Override
    public void writeAll(List<Classroom> classrooms) throws SchoolException {
        for (Classroom classroom : classrooms) {
            write(classroom);
        }
    }

    @Override
    public List<Classroom> readAll() throws SchoolException {
        List<Classroom> allClassrooms = new ArrayList<>();
        File folder = new File("database/classrooms/");
        File[] arrayOfFiles = folder.listFiles();
        if (arrayOfFiles != null) {
            List<File> listOfFiles = new ArrayList<>(Arrays.asList(arrayOfFiles));
            List<String> namesOfFiles = new ArrayList<>();
            listOfFiles.forEach((f) -> namesOfFiles.add(f.getName()));
            for (String nameOfFile : namesOfFiles) {
                Classroom classroom = read(nameOfFile);
                if (classroom.isActive()) {
                    allClassrooms.add(classroom);
                }
            }
            return allClassrooms;
        } else {
            throw new SchoolException("No Classrooms saved :(");
        }
    }
}
