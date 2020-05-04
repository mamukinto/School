package service.services.auth;

import model.Human;
import model.UserType;
import model.exception.SchoolException;

public interface AuthService {
    Human auth(String personalId, String password, UserType userType) throws SchoolException;
}