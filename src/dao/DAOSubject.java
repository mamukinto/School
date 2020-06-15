package dao;

import model.subject.Subject;
import model.exception.SchoolException;
import service.helpers.subject.SubjectFormatHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DAOSubject implements DAOService<Subject> {

    @Override
    public String write(Subject subject) throws SchoolException {
        SubjectFormatHelper subjectHelper = new SubjectFormatHelper();
        String formattedSubject = subjectHelper.format(subject);
        File file = new File("database/subjects/" + subject.getName() + ".txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(formattedSubject);
        } catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }

        return "Succesfully writed subject to " + file.getPath();
    }

    @Override
    public Subject read(String src) throws SchoolException {
        Subject subject = new Subject();
        SubjectFormatHelper subjectHelper = new SubjectFormatHelper();
        File classroomFile = new File("database/subjects/" + src);

        try(BufferedReader br = new BufferedReader(new FileReader(classroomFile))) {
            String strCurrentLine;
            StringBuilder subjectStringFromFile = new StringBuilder();
            while ((strCurrentLine = br.readLine()) != null) {
                subjectStringFromFile.append(strCurrentLine).append(System.lineSeparator());
            }

            subject = ((Subject) subjectHelper.parse(subjectStringFromFile.toString()));
        } catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }

        return subject;
    }

    @Override
    public void writeAll(List<Subject> subjects) throws SchoolException {
        for (Subject subject : subjects) {
            write(subject);
        }
    }

    @Override
    public List<Subject> readAll() throws SchoolException {
        List<Subject> allSubjects = new ArrayList<>();
        File folder = new File("database/subjects/");
        File[] arrayOfFiles = folder.listFiles();
        if  (arrayOfFiles != null) {
            List<File> listOfFiles = new ArrayList<>(Arrays.asList(arrayOfFiles));
            List<String> namesOfFiles = new ArrayList<>();
            listOfFiles.forEach((f) -> namesOfFiles.add(f.getName()));
            for (String nameOfFile : namesOfFiles) {
                Subject subject = read(nameOfFile);
                if (subject.isActive()) {
                    allSubjects.add(subject);
                }
            }
            return allSubjects;
        }
        else {
            throw new SchoolException("No Subjects saved :(");
        }
    }
}
