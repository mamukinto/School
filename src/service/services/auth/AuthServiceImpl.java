package service.services.auth;

import dao.DAODirector;
import dao.DAOService;
import dao.DAOStudent;
import dao.DAOTeacher;
import model.user.User;
import model.user.student.Student;
import model.user.teacher.Teacher;
import model.user.UserType;
import model.exception.SchoolException;
import service.services.student.StudentService;
import service.services.student.StudentServiceImpl;

import java.util.List;

public class AuthServiceImpl implements AuthService {

    public static User director = new Teacher();

    private final DAOService<Student> daoStudent = new DAOStudent();

    private final DAOService<Teacher> daoTeacher = new DAOTeacher();

    private final StudentService studentService = new StudentServiceImpl();

    private User user;

    @Override
    public User auth(String personalId, String password, UserType userType) throws SchoolException {
        director.setFirstName("Director");
        if (userType.equals(UserType.STUDENT)) {
                if (studentService.getStudentById(personalId).getPassword().equals("" + password.hashCode())) {
                    user = studentService.getStudentById(personalId);
                }
            }
         else if (userType.equals(UserType.TEACHER)) {
            daoTeacher.readAll().forEach((teacher -> {
                if (teacher.getPersonalId().equals(personalId)) {
                    if (teacher.getPassword().equals("" + password.hashCode())) {
                        user = teacher;
                    }
                }
            }));
        } else if (userType.equals(UserType.DIRECTOR)) {
            List<String> login = DAODirector.read();
            if (login.get(0).equals(personalId)) {
                if (login.get(1).equals("" + password.hashCode())) {
                    user = director;
                }
            }else {
                user = null;
            }
        }

        return user;
    }
}
