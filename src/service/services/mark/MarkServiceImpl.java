package service.services.mark;

import dao.DAOMark;
import model.Mark;
import model.Student;
import model.Teacher;
import model.exception.SchoolException;
import service.services.student.StudentService;
import service.services.student.StudentServiceImpl;

import java.time.LocalDate;
import java.util.List;

public class MarkServiceImpl implements MarkService {

    private static final DAOMark daoService = new DAOMark();

    private static final StudentService studentService = new StudentServiceImpl();

    @Override
    public List<Mark> getMarksOfStudent(Student student, Teacher teacher) throws SchoolException {
        List<Mark> marks;
        marks = daoService.readByStudentAndTeacher(student, teacher);
        return marks;
    }

    @Override
    public void addMarkToStudent(Student student, Teacher teacher, Mark mark, LocalDate date) throws SchoolException {
        mark.setDate(date);
        mark.setTeacher(teacher);
        mark.setStudent(student);
        daoService.write(mark);
        updateJournal(student);
    }

    @Override
    public void removeMark(Mark mark,Student student,Teacher teacher) throws SchoolException {
        mark.setActive(false);
        mark.setStudent(student);
        mark.setTeacher(teacher);
        daoService.write(mark);
    }

    public void updateJournal(Student student) {
        student.setJournal(daoService.readJournal(student));
    }

    @Override
    public void updateAllJournals() {
        studentService.getStudents().forEach(student -> updateJournal(student));
    }
}
