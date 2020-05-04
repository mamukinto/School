package service.services.mark;

import dao.DAOMark;
import dao.DAOService;
import model.Mark;
import model.Student;
import model.Teacher;
import model.exception.SchoolException;
import service.services.teacher.TeacherService;
import service.services.teacher.TeacherServiceImpl;

import java.util.Date;
import java.util.List;

public class MarkServiceImpl implements MarkService {
    private static final TeacherService teacherService = new TeacherServiceImpl();
    @Override
    public List<Mark> getMarksOfStudent(Student student, Teacher teacher) throws SchoolException {
        List<Mark> marks;
        marks = DAOMark.readAll(student, teacher);
        return marks;
    }

    @Override
    public void addMarkToStudent(Student student, Teacher teacher, Mark mark) throws SchoolException {
        Date writingDate = new Date();
        mark.setDate(writingDate);
        DAOMark.write(mark,student,teacher);
    }

    @Override
    public void removeMark(Mark mark,Student student,Teacher teacher) throws SchoolException {
        mark.setActive(false);
        DAOMark.write(mark,student,teacher);
    }

    public void updateJournal(Student student) {
        student.setJournal(DAOMark.readJournal(student));
    }

}
