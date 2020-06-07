package service.services.student;

import model.Mark;
import model.user.student.Student;
import model.Subject;
import model.exception.SchoolException;

import java.util.List;

public interface StudentService {

    void addStudent(Student student) throws SchoolException;

    void editStudent(Student student) throws SchoolException;

    Student getStudentById(String personalId) throws SchoolException;

    List<Student> getStudents();

    void updateStudents() throws SchoolException;

    void removeStudent(Student student) throws SchoolException;

    List<Mark> getMarksBySubject(Subject subject, Student student);

    double getAverageMarkOfStudentBySubject(Student student, Subject subject);

}