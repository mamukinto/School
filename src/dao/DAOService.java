package dao;

import model.DBObject;
import model.exception.SchoolException;

import java.util.List;

public interface DAOService<T extends DBObject> {

    String write(T dbObject) throws SchoolException;

    void writeAll(List<T> dbObjects) throws SchoolException;

    T read(String string) throws SchoolException;

    List<T> readAll() throws SchoolException;
}