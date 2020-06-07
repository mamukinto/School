package service.services.auth;

import model.user.User;
import model.user.UserType;
import model.exception.SchoolException;

public interface AuthService {
    User auth(String personalId, String password, UserType userType) throws SchoolException;
}