package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student extends Human {

    private Map<Subject, ArrayList<Mark>> journal;
    private Classroom classroom;


    public Student() {
        journal = new HashMap<>();
    }

    public Student(String firstName, String lastName, String personalId) {
        super(firstName, lastName, personalId);
        journal = new HashMap<>();
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
