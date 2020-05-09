package service.services;


import dao.*;
import model.Classroom;
import model.Student;
import model.Subject;
import model.Teacher;
import model.exception.SchoolException;
import storage.Storage;

public class StorageService {

    private static final DAOService<Classroom> classroomDAO = new DAOClassroom();

    private static final DAOService<Subject> subjectDAO = new DAOSubject();

    private static final DAOService<Student> studentDAO = new DAOStudent();

    private static final DAOService<Teacher> teacherDAO = new DAOTeacher();

    public static void updateStorage() throws SchoolException {
        Storage.classrooms = classroomDAO.readAll();
        Storage.subjects = subjectDAO.readAll();
        Storage.students = studentDAO.readAll();
        Storage.teachers = teacherDAO.readAll();
    }
}
