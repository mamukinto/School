package service.helpers.teacher;

import model.Classroom;
import model.DBObject;
import model.Teacher;
import service.services.StorageService;
import service.helpers.DBObjectFormatHelper;
import service.services.classroom.ClassroomsService;
import service.services.classroom.ClassroomsServiceImpl;
import service.services.subject.SubjectService;
import service.services.subject.SubjectServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class TeacherFormatHelper implements DBObjectFormatHelper {

    private static final ClassroomsService classroomsService = new ClassroomsServiceImpl();

    private static final SubjectService subjectService = new SubjectServiceImpl();

    @Override
    public Teacher parse(String dBObjectString) {
        Teacher teacher = new Teacher();
        String[] teacherLines = dBObjectString.split(LINE_SPLITTER);

        teacher.setFirstName(teacherLines[0].split(INFO_SPLITTER)[1].trim());
        teacher.setLastName(teacherLines[1].split(INFO_SPLITTER)[1].trim());
        teacher.setPersonalId(teacherLines[2].split(INFO_SPLITTER)[1].trim());
        teacher.setSubject(subjectService.getSubjectByName(teacherLines[3].split(INFO_SPLITTER)[1].trim()));
        teacher.setSalary(Double.parseDouble(teacherLines[4].split(INFO_SPLITTER)[1].trim()));

        List<Classroom> classrooms = new ArrayList<>();
        if (teacherLines[5].split(INFO_SPLITTER).length != 1) {
            String[] classroomStrings = teacherLines[6].split(INFO_SPLITTER)[1].split(INTEGER_LIST_SPLITTER);
        for (String classroomString : classroomStrings) {
            classrooms.add(classroomsService.getClassroomByName(classroomString));
        }
        }
        teacher.setActive(Boolean.parseBoolean(teacherLines[5].split(INFO_SPLITTER)[1].trim()));
        teacher.setClassrooms(classrooms);
        teacher.setEmail(teacherLines[7].split(INFO_SPLITTER)[1].trim());
        teacher.setPassword(teacherLines[8].split(INFO_SPLITTER)[1].trim());
        return teacher;
    }

    @Override
    public String format(DBObject dbObject) {
        Teacher teacher = (Teacher) dbObject;
        return teacher.getInfo();
    }
}
