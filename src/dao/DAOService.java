package dao;

import model.DBObject;
        import model.exception.SchoolException;

import java.util.List;

public interface DAOService {
    static String write(DBObject dbObject) throws SchoolException {
        return null;
    };
    static DBObject read(String string) throws SchoolException {
        return null;
    };
    static List<DBObject> readAll() {
        return null;
    }
}