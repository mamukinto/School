package service.services.student;

import model.Student;
import model.exception.SchoolException;

import java.util.List;

public interface StudentService {

    void addStudent(Student student) throws SchoolException;

    Student getStudentById(String personalId) throws SchoolException;

    List<Student> getStudents();

    void updateStudents() throws SchoolException;

    void removeStudent(Student student) throws SchoolException;

}