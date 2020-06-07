package service.services;


import dao.*;
import model.Classroom;
import model.user.student.Student;
import model.Subject;
import model.user.teacher.Teacher;
import model.exception.SchoolException;
import service.services.event.EventService;
import service.services.event.EventServiceImpl;
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

    private static final EventService eventService = new EventServiceImpl();

    public static void updateStorage() throws SchoolException {
        Storage.subjects = subjectDAO.readAll();
        Storage.teachers = teacherDAO.readAll();
        Storage.classrooms = classroomDAO.readAll();
        Storage.students = studentDAO.readAll();
        markService.updateAllJournals();
        eventService.updateAllEvents();
    }
}
