package service.services.mark;

import dao.DAOMark;
import model.mark.Mark;
import model.user.student.Student;
import model.subject.Subject;
import model.user.teacher.Teacher;
import model.exception.SchoolException;
import service.services.student.StudentService;
import service.services.student.StudentServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class MarkServiceImpl implements MarkService {

    private static final DAOMark daoService = new DAOMark();

    private static final StudentService studentService = new StudentServiceImpl();


    @Override
    public void addMarkToStudent(Student student, Teacher teacher, Mark mark, LocalDate date) throws SchoolException {
        validateMark(mark);
        mark.setDate(date);
        mark.setTeacher(teacher);
        mark.setStudent(student);
        daoService.write(mark);
        updateJournal(student);
    }

    @Override
    public void updateJournal(Student student) {
        Map<Subject, ArrayList<Mark>> journal = daoService.readJournal(student);
        student.setJournal(journal);
    }

    @Override
    public void updateAllJournals() {
        studentService.getStudents().forEach(this::updateJournal);
    }

    private void validateMark(Mark mark) throws SchoolException {
        if (mark.getValue() < 1 || mark.getValue() > 10) {
            throw new SchoolException("Mark value is not in range(1-10)");
        }
    }
}
