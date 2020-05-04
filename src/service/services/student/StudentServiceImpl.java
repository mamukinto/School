package service.services.student;

import dao.DAOStudent;
import model.Student;
import model.exception.SchoolException;
import service.email.EmailSender;
import service.helpers.auth.PasswordGenerator;
import storage.Storage;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    private static final int INITIAL_PASSWORD_LENGTH = 8;

    private static final String WELCOME_EMAIL_FROM_ADDRESS = "Java.Mziuri@gmail.com";

    private static final String WELCOME_EMAIL_SUBJECT = "System Registration";

    @Override
    public void addStudent(Student student) throws SchoolException {
        String password = PasswordGenerator.generatePassword(INITIAL_PASSWORD_LENGTH);
        student.setPassword("" + password.hashCode());
        DAOStudent.write(student);
        EmailSender.sendEmail(WELCOME_EMAIL_FROM_ADDRESS, student.getEmail(), WELCOME_EMAIL_SUBJECT, getEmailMessage(student.getFirstName(), password));
        updateStudents();
    }

    @Override
    public Student getStudentById(String personalId) throws SchoolException {
        final Student[] student = {new Student()};
        DAOStudent.readAll().forEach(s -> {
            if (s.getPersonalId().equals(personalId)) {
                student[0] = s;
            }
        });
        return student[0];
    }

    @Override
    public List<Student> getStudents() {
        return Storage.students;
    }

    @Override
    public void updateStudents() throws SchoolException {
        Storage.students = DAOStudent.readAll();
    }

    @Override
    public void removeStudent(Student student) throws SchoolException {
        student.setActive(false);
        DAOStudent.write(student);
        updateStudents();
    }

    private String getEmailMessage(String firstName, String password) {
        return firstName + ", your temporary password is " + password;
    }
}
