package service.services.student;

import model.mark.Mark;
import model.user.student.Student;
import model.subject.Subject;
import model.exception.SchoolException;
import model.user.student.SubjectWeekViewForStudent;

import java.time.LocalDate;
import java.util.List;

public interface StudentService {

    void addStudent(Student student) throws SchoolException;

    void editStudent(Student student) throws SchoolException;

    Student getStudentById(String personalId) throws SchoolException;

    List<Student> getStudents();

    void updateStudents() throws SchoolException;

    void removeStudent(Student student) throws SchoolException;

    List<Mark> getMarksBySubject(Subject subject, Student student);

    float getAverageMarkOfStudentBySubject(Student student, Subject subject);

    List<SubjectWeekViewForStudent> getSubjectWeekViewForStudent(Student student, LocalDate from);

}