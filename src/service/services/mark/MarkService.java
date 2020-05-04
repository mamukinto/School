package service.services.mark;

import model.Mark;
import model.Student;
import model.Teacher;
import model.exception.SchoolException;

import java.util.List;

public interface MarkService {
    List<Mark> getMarksOfStudent(Student student, Teacher teacher) throws SchoolException;

    void addMarkToStudent(Student student,Teacher teacher,Mark mark) throws SchoolException;

    void removeMark(Mark mark,Student student,Teacher teacher) throws SchoolException;

    void updateJournal(Student student);
}
