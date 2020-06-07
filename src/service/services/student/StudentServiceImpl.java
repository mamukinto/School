package service.services.student;

import dao.DAOService;
import dao.DAOStudent;
import model.Mark;
import model.user.student.Student;
import model.Subject;
import model.exception.SchoolException;
import service.email.EmailSender;
import service.helpers.auth.PasswordGenerator;
import storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    private static final int INITIAL_PASSWORD_LENGTH = 8;

    private static final String WELCOME_EMAIL_FROM_ADDRESS = "Java.Mziuri@gmail.com";

    private static final String WELCOME_EMAIL_SUBJECT = "System Registration";

    private final DAOService<Student> daoService = new DAOStudent();

    @Override
    public void addStudent(Student student) throws SchoolException {
        String password = PasswordGenerator.generatePassword(INITIAL_PASSWORD_LENGTH);
        student.setPassword("" + password.hashCode());
        daoService.write(student);
        try {
            EmailSender.sendEmail(WELCOME_EMAIL_FROM_ADDRESS, student.getEmail(), WELCOME_EMAIL_SUBJECT, getEmailMessage(student.getFirstName(), password));
        } catch (SchoolException e) {
            System.out.println(e.getMessage());
        }
        updateStudents();
    }

    @Override
    public void editStudent(Student student) throws SchoolException {
        daoService.write(student);
    }

    @Override
    public Student getStudentById(String personalId) throws SchoolException {
        for (Student student : Storage.students) {
            if (student.getPersonalId().equals(personalId)) {
                return student;
            }
        }
        return null;
    }

    @Override
    public List<Student> getStudents() {
        return Storage.students;
    }

    @Override
    public void updateStudents() throws SchoolException {
        Storage.students = daoService.readAll();
    }

    @Override
    public void removeStudent(Student student) throws SchoolException {
        student.setActive(false);
        daoService.write(student);
        updateStudents();
    }

    @Override
    public List<Mark> getMarksBySubject(Subject subject, Student student) {
        return student.getJournal().get(subject);
    }

    @Override
    public double getAverageMarkOfStudentBySubject(Student student, Subject subject) {
        List<Mark> marks = getMarksBySubject(subject, student);
        List<Integer> values = new ArrayList<>();
        marks.forEach(mark -> values.add(mark.getValue()));
        double sum = 0;
        for (Integer value : values) {
           sum += value;
        }
        return sum/values.size();
    }

    private String getEmailMessage(String firstName, String password) {
        return firstName + ", your temporary password is " + password;
    }
}
