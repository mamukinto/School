package service.services.auth;

import dao.DAODirector;
import dao.DAOStudent;
import dao.DAOTeacher;
import model.Human;
import model.Teacher;
import model.UserType;
import model.exception.SchoolException;

import java.util.List;

public class AuthServiceImpl implements AuthService {
    private Human human;
    public static Human director = new Teacher();
    @Override
    public Human auth(String personalId, String password, UserType userType) throws SchoolException {
        director.setFirstName("Director");
        if (userType.equals(UserType.STUDENT)) {
            DAOStudent.readAll().forEach((student -> {
                if (student.getPersonalId().equals(personalId)) {
                    if (student.getPassword().equals("" + password.hashCode())) {
                        human = student;
                    }
                }
            }));
        } else if (userType.equals(UserType.TEACHER)) {
            DAOTeacher.readAll().forEach((teacher -> {
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
