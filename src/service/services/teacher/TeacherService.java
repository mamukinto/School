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

    Teacher getTeacherByPersonalId(String personalId);

    List<Teacher> getTeachersBySubject(String subjectName);

    void addMarkToStudent(Teacher teacher, Mark mark, Student student) throws SchoolException;

    void removeTeacher(Teacher teacher) throws SchoolException;

    void updateTeachers() throws SchoolException;
}
