package service.services.event;

import dao.DAOEvent;
import model.event.Event;
import model.exception.SchoolException;
import model.user.student.Student;
import storage.Storage;

import java.util.Collections;
import java.util.List;

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
        List<Event> eventList = daoService.readAllByStudent(student.getPersonalId());
        Collections.sort(eventList);
        student.setEvents(eventList);
    }

    @Override
    public void updateAllEvents() throws SchoolException {
        for (Student student : Storage.students) {
            updateEventsOfStudent(student);
        }
    }

}
