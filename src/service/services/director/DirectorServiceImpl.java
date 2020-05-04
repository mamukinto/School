package service.services.director;

import dao.DAODirector;
import model.exception.SchoolException;

public class DirectorServiceImpl implements DirectorService {
    @Override
    public void addDirector(String id,String password) throws SchoolException {
        DAODirector.write(id,password);
    }
}
