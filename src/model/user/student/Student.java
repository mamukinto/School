package model.user.student;

import model.classrom.Classroom;
import model.event.Event;
import model.mark.Mark;
import model.subject.Subject;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends User {

    private Map<Subject, ArrayList<Mark>> journal;

    private List<Event> events;

    private Classroom classroom;

    public Student() {
        journal = new HashMap<>();
    }

    public Student(String firstName, String lastName, String personalId) {
        super(firstName, lastName, personalId);
        journal = new HashMap<>();
        events = new ArrayList<>();
    }

    public Map<Subject, ArrayList<Mark>> getJournal() {
        return journal;
    }

    public void setJournal(Map<Subject, ArrayList<Mark>> journal) {
        this.journal = journal;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public String getInfo() {
        return "Name:" + getFirstName() + System.lineSeparator() +
                "LastName:" + getLastName() + System.lineSeparator() +
                "PersonalId:" + getPersonalId() + System.lineSeparator() +
                "Classroom:" + getClassroom().getName() + System.lineSeparator() +
                "Email:" + getEmail() + System.lineSeparator() +
                "Password:" + getPassword() + System.lineSeparator() +
                "Active:" + isActive();
    }
}
