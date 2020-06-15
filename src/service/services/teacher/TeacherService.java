package service.services.teacher;

import model.classrom.Classroom;
import model.exception.SchoolException;
import model.mark.Mark;
import model.user.student.Student;
import model.user.student.StudentWeekView;
import model.user.teacher.Teacher;

import java.time.LocalDate;
import java.util.List;

public interface TeacherService {

    void addTeacher(Teacher teacher) throws SchoolException;

    void editTeacher(Teacher teacher) throws SchoolException;

    List<Teacher> getTeachers();

    Teacher getTeacherByPersonalId(String personalId);

    List<Teacher> getTeachersBySubject(String subjectName);

    List<StudentWeekView>   getTeachersStudentWeekViews(Teacher teacher, Classroom classroom, LocalDate from);

    void addMarkToStudent(Teacher teacher, Mark mark, Student student, LocalDate date) throws SchoolException;

    void removeTeacher(Teacher teacher) throws SchoolException;

    void updateTeachers() throws SchoolException;
}
