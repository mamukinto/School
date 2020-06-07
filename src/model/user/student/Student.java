package model.user.student;

import model.Classroom;
import model.Event;
import model.Mark;
import model.Subject;
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
        StringBuilder info = new StringBuilder();
        info.append("Name:").append(getFirstName()).append(System.lineSeparator());
        info.append("LastName:").append(getLastName()).append(System.lineSeparator());
        info.append("PersonalId:").append(getPersonalId()).append(System.lineSeparator());
        info.append("Classroom:").append(getClassroom().getName()).append(System.lineSeparator());
        info.append("Email:").append(getEmail()).append(System.lineSeparator());
        info.append("Password:").append(getPassword()).append(System.lineSeparator());
        info.append("Active:").append(isActive());


        return info.toString();
    }


}
