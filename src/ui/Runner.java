package ui;

import model.Classroom;
import model.Subject;
import model.Teacher;
import model.exception.SchoolException;
import service.Startup;
import service.services.classroom.ClassroomsService;
import service.services.classroom.ClassroomsServiceImpl;
import service.services.subject.SubjectService;
import service.services.subject.SubjectServiceImpl;
import service.services.teacher.TeacherService;
import service.services.teacher.TeacherServiceImpl;

import java.util.HashMap;

public class Runner {

    public static void main(String[] args) throws SchoolException {
        Startup.startUp();
//        UserInterface.launch();
        Classroom classroom = new Classroom();
        classroom.setId(1212312321);
        classroom.setName("12");
        HashMap<Subject, Teacher> teachers = new HashMap<>();

        Subject maths = new Subject();
        maths.setName("maths");
        maths.setCostPerHour(1);
        SubjectService subjectService = new SubjectServiceImpl();
        subjectService.addSubject(maths);


        Teacher teacher = new Teacher();
        teacher.setPersonalId("123213123123");
        teacher.setFirstName("ra");
        teacher.setLastName("vin");
        teacher.setEmail("123@123.123");
        teacher.setSubject(maths);
        TeacherService teacherService = new TeacherServiceImpl();
        try {
            teacherService.addTeacher(teacher);
        } catch (SchoolException e) {
            e.printStackTrace();
        }


        teachers.put(maths,teacher);
        classroom.setTeachers(teachers);
        ClassroomsService classroomsService = new ClassroomsServiceImpl();
        try {
            classroomsService.addClassroom(classroom);
        } catch (SchoolException e) {
            e.printStackTrace();
        }

    }
}

