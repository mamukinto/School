package service.services.classroom;

import dao.DAOClassroom;
import dao.DAOService;
import model.classrom.Classroom;
import model.subject.Subject;
import model.user.student.Student;
import model.user.teacher.Teacher;
import model.exception.SchoolException;
import service.services.student.StudentService;
import service.services.student.StudentServiceImpl;
import storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassroomsServiceImpl implements ClassroomsService {

    private final DAOService<Classroom> daoService = new DAOClassroom();

    private final StudentService studentService = new StudentServiceImpl();


    @Override
    public List<Classroom> getClassrooms() {
        Storage.classrooms.forEach(classroom -> classroom.setSubjectTeachersString(teachersStringFromHashMap(classroom.getTeachers())));
        return Storage.classrooms;
    }


    @Override
    public List<Classroom> getClassroomsByTeacher(Teacher teacher) {
        List<Classroom> classrooms = new ArrayList<>();
        getClassrooms().forEach(classroom -> {
            if (classroom.getTeachers().containsValue(teacher)) {
                classrooms.add(classroom);
            }
        });
        return classrooms;
    }

    @Override
    public Classroom getClassroomByName(String classroomName) {
        Classroom classroom = new Classroom();
        List<Classroom> classrooms = Storage.classrooms;
        for (Classroom value : classrooms) {
            if (value.getName().equals(classroomName)) {
                classroom = value;
            }
        }
        return classroom;
    }

    @Override
    public List<Student> getStudentsFromClassroom(Classroom classroom) {
        List<Student> students = new ArrayList<>();
        studentService.getStudents().forEach(student -> {
            if (student.getClassroom().equals(classroom)) {
                students.add(student);
            }
        });
        return students;
    }

    @Override
    public void editClassroom(Classroom classroom) throws SchoolException {
        daoService.write(classroom);
    }

    @Override
    public void addClassroom(Classroom classroom) throws SchoolException {
        daoService.write(classroom);
        updateClassrooms();
    }

    @Override
    public void removeClassroom(Classroom classroom) throws SchoolException {
        classroom.setActive(false);
        daoService.write(classroom);
        updateClassrooms();
    }

    @Override
    public void updateClassrooms() throws SchoolException {
        Storage.classrooms = daoService.readAll();
    }


    private String teachersStringFromHashMap(Map<Subject, Teacher> subjectTeacherMap) {
        StringBuilder temp = new StringBuilder();
        subjectTeacherMap.forEach((subject, teacher) -> {
            temp.append(subject.getName())
                    .append("(")
                    .append(teacher.getFirstName())
                    .append(" ")
                    .append(teacher.getLastName())
                    .append("), ");
        });
        String result = temp.toString();
        if (result.isEmpty()) {
            return result;
        } else {
            return result.substring(0,result.length() - 2);
        }
    }
}
