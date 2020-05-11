package dao;

import model.*;
import model.exception.SchoolException;
import service.helpers.mark.MarkFormatHelper;
import service.services.subject.SubjectService;
import service.services.subject.SubjectServiceImpl;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class DAOMark implements DAOService<Mark> {

    @Override
    public String write(Mark mark) throws SchoolException {
        MarkFormatHelper markFormatHelperHelper = new MarkFormatHelper();
        String formattedMark = markFormatHelperHelper.format(mark);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = dateFormat.format(mark.getDate());
        File file = new File("database/students/" + mark.getStudent().getPersonalId() + "/" + mark.getTeacher().getSubject().getName() + "/" + formattedDate + ".txt");
        file.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(formattedMark);
        } catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }

        return "Succesfully writed mark to " + file.getPath();
    }

    @Override
    public void writeAll(List<Mark> dbObjects) throws SchoolException {
        throw new NotImplementedException();
    }

    @Override
    public Mark read(String nameOfFile) throws SchoolException {
        Mark mark;
        MarkFormatHelper markHelper = new MarkFormatHelper();
        File file = new File("database/students/" + nameOfFile);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String strCurrentLine;
            StringBuilder markStringFromFile = new StringBuilder();
            while ((strCurrentLine = br.readLine()) != null) {
                markStringFromFile.append(strCurrentLine).append(System.lineSeparator());
            }
            mark = (Mark) markHelper.parse(markStringFromFile.toString());
        } catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }

        return mark;
    }

    @Override
    public List<Mark> readAll() throws SchoolException {
        throw new NotImplementedException();
    }

    public List<Mark> readByStudentAndTeacher(Student student, Teacher teacher) throws SchoolException {
        List<Mark> allMarks = new ArrayList<>();
        File folder = new File("database/students/" + student.getPersonalId() + "/" + teacher.getSubject().getName() + "/");
        File[] arrayOfFiles = folder.listFiles();
        if (arrayOfFiles != null) {
            List<File> listOfFiles = new ArrayList<>(Arrays.asList(arrayOfFiles));
            List<String> namesOfFiles = new ArrayList<>();
            listOfFiles.forEach((f) -> namesOfFiles.add(f.getName()));
            for (String nameOfFile : namesOfFiles) {
                allMarks.add((Mark) read(student.getPersonalId() + "/" + teacher.getSubject().getName() + "/" + nameOfFile));
            }
            return allMarks;
        } else {
            throw new SchoolException("No students saved :(");
        }
    }

    public Map<Subject, ArrayList<Mark>> readJournal(Student student) {
        Map<Subject, ArrayList<Mark>> journal = new HashMap<>();
        File file = new File("database/students/" + student.getPersonalId() + "/");
        File[] arrayOfFolders = file.listFiles(File::isDirectory);
        SubjectService subjectService = new SubjectServiceImpl();
        if (arrayOfFolders != null) {
            List<File> listOfFolders = new ArrayList<>(Arrays.asList(arrayOfFolders));
            List<String> namesOfFolders = new ArrayList<>();
            listOfFolders.forEach(folder -> namesOfFolders.add(folder.getName()));
            namesOfFolders.forEach(nameOfFolder -> {
                journal.computeIfAbsent(subjectService.getSubjectByName(nameOfFolder), k -> (ArrayList<Mark>) readByStudentAndSubjectName(student, nameOfFolder));
            });
        }

        return journal;
    }

    private  List<Mark> readByStudentAndSubjectName(Student student, String subjectName) {
        List<Mark> allMarks = new ArrayList<>();
        File folder = new File("database/students/" + student.getPersonalId() + "/" + subjectName + "/");
        File[] arrayOfFiles = folder.listFiles();
        List<File> listOfFiles = new ArrayList<>(Arrays.asList(arrayOfFiles));
        List<String> namesOfFiles = new ArrayList<>();
        listOfFiles.forEach((f) -> namesOfFiles.add(f.getName()));
        for (String nameOfFile : namesOfFiles) {
            try {
                Mark mark = (Mark) read(student.getPersonalId() + "/" + subjectName + "/" + nameOfFile);
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


}
