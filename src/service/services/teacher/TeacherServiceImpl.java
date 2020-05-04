package service.services.teacher;

import dao.DAOStudent;
import dao.DAOTeacher;
import model.Classroom;
import model.Mark;
import model.Student;
import model.Teacher;
import model.exception.SchoolException;
import service.email.EmailSender;
import service.helpers.auth.PasswordGenerator;
import service.services.mark.MarkService;
import service.services.mark.MarkServiceImpl;
import service.services.student.StudentService;
import service.services.student.StudentServiceImpl;
import storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    private static final StudentService studentService = new StudentServiceImpl();

    private static final MarkService markService = new MarkServiceImpl();

    private static final int INITIAL_PASSWORD_LENGTH = 8;

    private static final String WELCOME_EMAIL_FROM_ADDRESS = "Java.Mziuri@gmail.com";

    private static final String WELCOME_EMAIL_SUBJECT = "System Registration";


    @Override
    public void addTeacher(Teacher teacher) throws SchoolException {
        String password = PasswordGenerator.generatePassword(INITIAL_PASSWORD_LENGTH);
        teacher.setPassword("" + password.hashCode());
        DAOTeacher.write(teacher);
        EmailSender.sendEmail(WELCOME_EMAIL_FROM_ADDRESS, teacher.getEmail(), WELCOME_EMAIL_SUBJECT, getEmailMessage(teacher.getFirstName(), password));
    }

    @Override
    public List<Teacher> getTeachers() {
        return Storage.teachers;
    }

    @Override
    public void addMarkToStudent(Teacher teacher, Mark mark, Student student) throws SchoolException {
        markService.addMarkToStudent(student, teacher, mark);
    }

    @Override
    public List<Student> getTeacherStudents(Teacher teacher) {
        List<Student> result = new ArrayList<>();
        List<Classroom> classrooms = teacher.getClassrooms();
        for (Student student : Storage.students) {
            for (Classroom classroom : classrooms) {
                if (student.getClassroom().equals(classroom)) {
                    result.add(student);
                }
            }
        }
        return result;
    }

    @Override
    public void removeTeacher(Teacher teacher) throws SchoolException {
        teacher.setActive(false);
        DAOTeacher.write(teacher);
    }

    @Override
    public void updateTeachers() throws SchoolException {
        Storage.teachers = DAOTeacher.readAll();
    }

    private String getEmailMessage(String firstName, String password) {
        return firstName + ", your temporary password is " + password;
    }
}
