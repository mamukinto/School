package service.services.teacher;

import model.Mark;
import model.Student;
import model.Teacher;
import model.exception.SchoolException;

import java.util.List;

public interface TeacherService {

    void addTeacher(Teacher teacher) throws SchoolException;

    void editTeacher(Teacher teacher) throws SchoolException;

    List<Teacher> getTeachers();

    void addMarkToStudent(Teacher teacher, Mark mark, Student student) throws SchoolException;

    List<Student> getTeacherStudents(Teacher teacher);

    void removeTeacher(Teacher teacher) throws SchoolException;

    void updateTeachers() throws SchoolException;
}
