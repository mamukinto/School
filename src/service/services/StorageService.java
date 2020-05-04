package service.services;


import dao.DAOClassroom;
import dao.DAOStudent;
import dao.DAOSubject;
import dao.DAOTeacher;
import model.exception.SchoolException;
import storage.Storage;

public class StorageService {

    public static void updateStorage() throws SchoolException {
        Storage.classrooms = DAOClassroom.readAll();
        Storage.subjects = DAOSubject.readAll();
        Storage.students = DAOStudent.readAll();
        Storage.teachers = DAOTeacher.readAll();
    }

}
