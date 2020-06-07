package service.helpers.student;

import model.DBObject;
import model.Mark;
import model.user.student.Student;
import service.helpers.DBObjectFormatHelper;
import service.services.classroom.ClassroomsService;
import service.services.classroom.ClassroomsServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentFormatHelper implements DBObjectFormatHelper {

    private static final ClassroomsService classroomsService = new ClassroomsServiceImpl();

    private ArrayList<Mark> stringsToMarks(String[] strs) {
        ArrayList<String> strsList = new ArrayList<>(Arrays.asList(strs));
        ArrayList<Mark> marks = new ArrayList<>();
        strsList.forEach((v) -> marks.add(new Mark(Integer.parseInt(v.trim()))));
        return marks;
    }

    @Override
    public DBObject parse(String dBObjectString) {
        Student student = new Student();

        String[] studentLines = dBObjectString.split(LINE_SPLITTER);

        int index = 0;
        student.setFirstName(studentLines[index++].split(INFO_SPLITTER)[1].trim());
        student.setLastName(studentLines[index++].split(INFO_SPLITTER)[1].trim());
        student.setPersonalId(studentLines[index++].split(INFO_SPLITTER)[1].trim());
        student.setClassroom(classroomsService.getClassroomByName(studentLines[index++].split(INFO_SPLITTER)[1].trim()));
        student.setEmail(studentLines[index++].split(INFO_SPLITTER)[1].trim());
        student.setPassword(studentLines[index++].split(INFO_SPLITTER)[1].trim());
        student.setActive(Boolean.parseBoolean(studentLines[index++].split(INFO_SPLITTER)[1].trim()));

        return student;
    }

    @Override
    public String format(DBObject dbObject) {
        Student student = (Student) dbObject;
        return student.getInfo();
    }
}
