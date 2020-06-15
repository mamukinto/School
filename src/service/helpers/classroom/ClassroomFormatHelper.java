package service.helpers.classroom;

import model.classrom.Classroom;
import model.DBObject;
import model.subject.Subject;
import model.user.teacher.Teacher;
import service.helpers.DBObjectFormatHelper;
import service.services.subject.SubjectService;
import service.services.subject.SubjectServiceImpl;
import service.services.teacher.TeacherService;
import service.services.teacher.TeacherServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ClassroomFormatHelper implements DBObjectFormatHelper {

    @Override
    public DBObject parse(String DBObjectString) {
        Classroom classroom = new Classroom();
        String[] classroomLines = DBObjectString.split(LINE_SPLITTER);
        classroom.setName(classroomLines[0].split(INFO_SPLITTER)[1]);
        classroom.setId(Integer.parseInt(classroomLines[1].split(INFO_SPLITTER)[1]));
        classroom.setActive(Boolean.parseBoolean(classroomLines[2].split(INFO_SPLITTER)[1].trim()));
        classroom.setTeachers(getTeachersMap(classroomLines[3]));
        return classroom;
    }

    @Override
    public String format(DBObject dbObject) {
        return ((Classroom) dbObject).getInfo();
    }

    private HashMap<Subject, Teacher> getTeachersMap(String teachersString) {
        HashMap<Subject,Teacher> teachers = new HashMap<>();
        List<String> mapEntries = new ArrayList<>(Arrays.asList(teachersString.split(MAP_SPLITTER)));
        SubjectService subjectService = new SubjectServiceImpl();
        TeacherService teacherService = new TeacherServiceImpl();
        mapEntries.forEach(entry -> teachers.put(subjectService.getSubjectByName(entry.split(MAP_EQUALLY)[0]),teacherService.getTeacherByPersonalId(entry.split(MAP_EQUALLY)[1])));
        return teachers;
    }
}
