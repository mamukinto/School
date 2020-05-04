package dao;

import model.*;
import model.exception.SchoolException;
import service.helpers.mark.MarkFormatHelper;
import service.services.subject.SubjectService;
import service.services.subject.SubjectServiceImpl;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class DAOMark implements DAOService {
    public static String write(DBObject dbObject, Student student, Teacher teacher) throws SchoolException {
        Mark mark = (Mark) dbObject;
        MarkFormatHelper markFormatHelperHelper = new MarkFormatHelper();
        String formattedMark = markFormatHelperHelper.format(mark);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = dateFormat.format(mark.getDate());
        File file = new File("database/students/" + student.getPersonalId() + "/" + teacher.getSubject().getName() + "/" + formattedDate + ".txt");
        file.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(formattedMark);
        }
        catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }


        return "Succesfully writed mark to " + file.getPath();
    };

    public static String write(Mark mark,Student student,Teacher teacher) throws SchoolException {
        MarkFormatHelper markFormatHelperHelper = new MarkFormatHelper();
        String formattedMark = markFormatHelperHelper.format(mark);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = dateFormat.format(mark.getDate());
        File file = new File("database/students/" + student.getPersonalId() + "/" + teacher.getSubject().getName() + "/" + formattedDate + ".txt");
        file.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(formattedMark);
        }
        catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }


        return "Succesfully writed mark to " + file.getPath();
    };
    private static DBObject read(String nameOfFile, Student student,String subjectName) throws SchoolException {
        Mark mark = new Mark();
        MarkFormatHelper markHelper = new MarkFormatHelper();
        File file = new File("database/students/" + student.getPersonalId() + "/" + subjectName + "/" + nameOfFile);

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String strCurrentLine;
            StringBuilder markStringFromFile = new StringBuilder();
            while ((strCurrentLine = br.readLine()) != null) {
                markStringFromFile.append(strCurrentLine).append(System.lineSeparator());
            }
            mark = (Mark) markHelper.parse(markStringFromFile.toString());
        }
        catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }

        return mark;
    };

    public static List<Mark> readAll(Student student, Teacher teacher) throws SchoolException {
        List<Mark> allMarks = new ArrayList<>();
        File folder = new File("database/students/" + student.getPersonalId() + "/" + teacher.getSubject().getName() + "/");
        File[] arrayOfFiles = folder.listFiles();
        if  (arrayOfFiles != null) {
            List<File> listOfFiles = new ArrayList<>(Arrays.asList(arrayOfFiles));
            List<String> namesOfFiles = new ArrayList<>();
            listOfFiles.forEach((f) -> namesOfFiles.add(f.getName()));
            for (String nameOfFile : namesOfFiles) {
                allMarks.add((Mark) read(nameOfFile,student,teacher.getSubject().getName()));
            }
            return allMarks;
        }
        else {
            throw new SchoolException("No students saved :(");
        }
    }


    public static List<Mark> readAll(Student student,String subjectName){
        List<Mark> allMarks = new ArrayList<>();
        File folder = new File("database/students/" + student.getPersonalId() + "/" + subjectName + "/");
        File[] arrayOfFiles = folder.listFiles();
            List<File> listOfFiles = new ArrayList<>(Arrays.asList(arrayOfFiles));
            List<String> namesOfFiles = new ArrayList<>();
            listOfFiles.forEach((f) -> namesOfFiles.add(f.getName()));
            for (String nameOfFile : namesOfFiles) {
                try {
                    Mark mark = (Mark) read(nameOfFile,student,subjectName);
                    if (mark.isActive()) {
                        allMarks.add(mark);
                    }
                } catch (SchoolException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
            return allMarks;
    }

    public static Map<Subject,ArrayList<Mark>> readJournal(Student student) {
        Map<Subject,ArrayList<Mark>> journal = new HashMap<>();
        File file = new File("database/students/" + student.getPersonalId() + "/");
        File[] arrayOfFolders = file.listFiles(File::isDirectory);
        SubjectService subjectService = new SubjectServiceImpl();
        if (arrayOfFolders != null) {
            List<File> listOfFolders = new ArrayList<>(Arrays.asList(arrayOfFolders));
            List<String> namesOfFolders = new ArrayList<>();
            listOfFolders.forEach(folder -> namesOfFolders.add(folder.getName()));
            namesOfFolders.forEach(nameOfFolder -> {
                journal.computeIfAbsent(subjectService.getSubjectByName(nameOfFolder), k -> (ArrayList<Mark>) readAll(student,nameOfFolder));
            });
        }

        return journal;
    }
}
