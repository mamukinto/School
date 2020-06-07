package service.services.event;

import dao.DAOEvent;
import model.Event;
import model.exception.SchoolException;
import model.user.student.Student;
import storage.Storage;

public class EventServiceImpl implements EventService {
    DAOEvent daoService = new DAOEvent();

    @Override
    public void addEvent(Event event, Student student) throws SchoolException {
        event.setStudentPersonalId(student.getPersonalId());
        daoService.write(event);
        updateEventsOfStudent(student);
    }

    @Override
    public void updateEventsOfStudent(Student student) throws SchoolException {
        student.setEvents(daoService.readAllByStudent(student.getPersonalId()));
    }

    @Override
    public void updateAllEvents() throws SchoolException {
        for (Student student : Storage.students) {
            updateEventsOfStudent(student);
        }
    }

}
