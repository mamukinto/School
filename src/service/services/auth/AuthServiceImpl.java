package service.services.auth;

import dao.DAODirector;
import dao.DAOService;
import dao.DAOStudent;
import dao.DAOTeacher;
import model.Human;
import model.Student;
import model.Teacher;
import model.UserType;
import model.exception.SchoolException;

import java.util.List;

public class AuthServiceImpl implements AuthService {

    public static Human director = new Teacher();

    private final DAOService<Student> daoStudent = new DAOStudent();

    private final DAOService<Teacher> daoTeacher = new DAOTeacher();

    private Human human;

    @Override
    public Human auth(String personalId, String password, UserType userType) throws SchoolException {
        director.setFirstName("Director");
        if (userType.equals(UserType.STUDENT)) {
            daoStudent.readAll().forEach((student -> {
                if (student.getPersonalId().equals(personalId)) {
                    if (student.getPassword().equals("" + password.hashCode())) {
                        human = student;
                    }
                }
            }));
        } else if (userType.equals(UserType.TEACHER)) {
            daoTeacher.readAll().forEach((teacher -> {
                if (teacher.getPersonalId().equals(personalId)) {
                    if (teacher.getPassword().equals("" + password.hashCode())) {
                        human = teacher;
                    }
                }
            }));
        } else if (userType.equals(UserType.DIRECTOR)) {
            List<String> login = DAODirector.read();
            if (login.get(0).equals(personalId)) {
                if (login.get(1).equals("" + password.hashCode())) {
                    human = director;
                }
            }else {
                human = null;
            }
        }

        return human;
    }
}
