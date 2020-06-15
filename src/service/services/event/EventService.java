package service.services.event;

import model.event.Event;
import model.exception.SchoolException;
import model.user.student.Student;

public interface EventService{

    void addEvent(Event event,Student student) throws SchoolException;

    void updateEventsOfStudent(Student student) throws SchoolException;

    void updateAllEvents() throws SchoolException;
}
