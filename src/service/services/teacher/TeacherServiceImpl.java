package service.services.teacher;

import dao.DAOService;
import dao.DAOTeacher;
import model.Mark;
import model.Student;
import model.Teacher;
import model.Classroom;
import model.exception.SchoolException;
import service.email.EmailSender;
import service.helpers.auth.PasswordGenerator;
import service.services.mark.MarkService;
import service.services.mark.MarkServiceImpl;
import storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    private static final MarkService markService = new MarkServiceImpl();

    private static final int INITIAL_PASSWORD_LENGTH = 8;

    private static final String WELCOME_EMAIL_FROM_ADDRESS = "Java.Mziuri@gmail.com";

    private static final String WELCOME_EMAIL_SUBJECT = "System Registration";

    private DAOService<Teacher> daoService = new DAOTeacher();

    @Override
    public void addTeacher(Teacher teacher) throws SchoolException {
        String password = PasswordGenerator.generatePassword(INITIAL_PASSWORD_LENGTH);
        teacher.setPassword("" + password.hashCode());
        daoService.write(teacher);
        try {
            EmailSender.sendEmail(WELCOME_EMAIL_FROM_ADDRESS, teacher.getEmail(), WELCOME_EMAIL_SUBJECT, getEmailMessage(teacher.getFirstName(), password));
        } catch (SchoolException e) {
            System.out.println("Cant send email -> " + e.getMessage());
        }
        updateTeachers();
    }

    @Override
    public void editTeacher(Teacher teacher) throws SchoolException {
        daoService.write(teacher);
        updateTeachers();
    }

    @Override
    public List<Teacher> getTeachers() {
        return Storage.teachers;
    }

    @Override
    public Teacher getTeacherByPersonalId(String personalId) {
        for (Teacher teacher : Storage.teachers) {
            if (teacher.getPersonalId().equals(personalId)) {
                return teacher;
            }
        }
        return null;
    }

    @Override
    public List<Teacher> getTeachersBySubject(String subjectName) {
        List<Teacher> teachersOfThisSubject = new ArrayList<>();
        getTeachers().forEach(teacher -> {
            if (teacher.getSubject().getName().equals(subjectName)) {
                teachersOfThisSubject.add(teacher);
            }
        });
        return teachersOfThisSubject;
    }

    @Override
    public void addMarkToStudent(Teacher teacher, Mark mark, Student student) throws SchoolException {
        markService.addMarkToStudent(student, teacher, mark);
    }


    @Override
    public void removeTeacher(Teacher teacher) throws SchoolException {
        teacher.setActive(false);
        daoService.write(teacher);
        updateTeachers();
    }

    @Override
    public void updateTeachers() throws SchoolException {
        Storage.teachers = daoService.readAll();
    }

    private String getEmailMessage(String firstName, String password) {
        return firstName + ", your temporary password is " + password;
    }
}
