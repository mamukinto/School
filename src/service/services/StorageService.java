package service.services;


import dao.*;
import model.Classroom;
import model.Student;
import model.Subject;
import model.Teacher;
import model.exception.SchoolException;
import service.services.mark.MarkService;
import service.services.mark.MarkServiceImpl;
import storage.Storage;

public class StorageService {

    private static final DAOService<Classroom> classroomDAO = new DAOClassroom();

    private static final DAOService<Subject> subjectDAO = new DAOSubject();

    private static final DAOService<Student> studentDAO = new DAOStudent();

    private static final DAOService<Teacher> teacherDAO = new DAOTeacher();

    private static final DAOMark daoMark = new DAOMark();

    private static final MarkService markService = new MarkServiceImpl();

    public static void updateStorage() throws SchoolException {
        Storage.subjects = subjectDAO.readAll();
        Storage.teachers = teacherDAO.readAll();
        Storage.classrooms = classroomDAO.readAll();
        Storage.students = studentDAO.readAll();
        markService.updateAllJournals();
    }
}
