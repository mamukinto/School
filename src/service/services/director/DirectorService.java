package service.services.director;

import model.exception.SchoolException;

public interface DirectorService {
    void addDirector(String id,String password) throws SchoolException;
}
