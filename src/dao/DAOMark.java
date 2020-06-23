package dao;

import model.exception.SchoolException;
import model.mark.Mark;
import model.subject.Subject;
import model.user.student.Student;
import service.helpers.mark.MarkFormatHelper;
import service.services.subject.SubjectService;
import service.services.subject.SubjectServiceImpl;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.DateFormatsUtils;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DAOMark implements DAOService<Mark> {

    @Override
    public String write(Mark mark) throws SchoolException {
        MarkFormatHelper markFormatHelperHelper = new MarkFormatHelper();
        String formattedMark = markFormatHelperHelper.format(mark);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DateFormatsUtils.DATE_FORMAT_FOR_FILE_NAME);
        String formattedDate = dateFormat.format(mark.getDate());
        File file = new File("database/students/" + mark.getStudent().getPersonalId() + "/marks/" + mark.getTeacher().getSubject().getName() + "/" + formattedDate + ".txt");
        file.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(formattedMark);
        } catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }

        return "Successfully written mark to " + file.getPath();
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
    public List<Mark> readAll() {
        throw new NotImplementedException();
    }

    public Map<Subject, ArrayList<Mark>> readJournal(Student student) {
        Map<Subject, ArrayList<Mark>> journal = new HashMap<>();
        File file = new File("database/students/" + student.getPersonalId() + "/marks/");
        File[] arrayOfFolders = file.listFiles(File::isDirectory);
        SubjectService subjectService = new SubjectServiceImpl();
        if (arrayOfFolders != null) {
            List<File> listOfFolders = new ArrayList<>(Arrays.asList(arrayOfFolders));
            List<String> namesOfFolders = new ArrayList<>();
            listOfFolders.forEach(folder -> namesOfFolders.add(folder.getName()));
            namesOfFolders.forEach(nameOfFolder -> journal.computeIfAbsent(subjectService.getSubjectByName(nameOfFolder), k -> (ArrayList<Mark>) readByStudentAndSubjectName(student, nameOfFolder)));
        }

        return journal;
    }

    private  List<Mark> readByStudentAndSubjectName(Student student, String subjectName) {
        List<Mark> allMarks = new ArrayList<>();
        File folder = new File("database/students/" + student.getPersonalId() + "/marks/" + subjectName + "/");
        File[] arrayOfFiles = folder.listFiles();
        List<File> listOfFiles = new ArrayList<>(Arrays.asList(arrayOfFiles));
        List<String> namesOfFiles = new ArrayList<>();
        listOfFiles.forEach((f) -> namesOfFiles.add(f.getName()));
        for (String nameOfFile : namesOfFiles) {
            try {
                Mark mark = read(student.getPersonalId() + "/marks/" + subjectName + "/" + nameOfFile);
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
