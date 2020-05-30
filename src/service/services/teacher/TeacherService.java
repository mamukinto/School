package service.services.teacher;

import model.*;
import model.exception.SchoolException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TeacherService {

    void addTeacher(Teacher teacher) throws SchoolException;

    void editTeacher(Teacher teacher) throws SchoolException;

    List<Teacher> getTeachers();

    Teacher getTeacherByPersonalId(String personalId);

    List<Teacher> getTeachersBySubject(String subjectName);

    List<StudentWeekView> getTeachersStudentWeekViews(Teacher teacher, Classroom classroom, String searchName, LocalDate from);

    void addMarkToStudent(Teacher teacher, Mark mark, Student student) throws SchoolException;

    void removeTeacher(Teacher teacher) throws SchoolException;

    void updateTeachers() throws SchoolException;
}
