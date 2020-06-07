package service.services.mark;

import model.Mark;
import model.user.student.Student;
import model.user.teacher.Teacher;
import model.exception.SchoolException;

import java.time.LocalDate;
import java.util.List;

public interface MarkService {

    void addMarkToStudent(Student student,Teacher teacher,Mark mark, LocalDate date) throws SchoolException;

    void updateJournal(Student student);

    void updateAllJournals();
}
