package service.services.mark;

import dao.DAOMark;
import model.Mark;
import model.Student;
import model.Teacher;
import model.exception.SchoolException;

import java.util.Date;
import java.util.List;

public class MarkServiceImpl implements MarkService {

    private static final DAOMark daoService = new DAOMark();

    @Override
    public List<Mark> getMarksOfStudent(Student student, Teacher teacher) throws SchoolException {
        List<Mark> marks;
        marks = daoService.readByStudentAndTeacher(student, teacher);
        return marks;
    }

    @Override
    public void addMarkToStudent(Student student, Teacher teacher, Mark mark) throws SchoolException {
        Date writingDate = new Date();
        mark.setDate(writingDate);
        mark.setTeacher(teacher);
        mark.setStudent(student);
        daoService.write(mark);
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
}
