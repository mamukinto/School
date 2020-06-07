package model.user.teacher;

import model.Subject;
import model.user.User;

public class Teacher extends User {
    private Subject subject;
    private double salary;

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, String personalId) {
        super(firstName, lastName, personalId);
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public double getSalary() {
        salary = subject.getCostPerHour()*12;
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Name:").append(getFirstName()).append(System.lineSeparator());
        info.append("LastName:").append(getLastName()).append(System.lineSeparator());
        info.append("PersonalId:").append(getPersonalId()).append(System.lineSeparator());

        if  (getSubject() != null) {
            info.append("Subject:").append(getSubject().getName()).append(System.lineSeparator());
        }

        info.append("Salary:").append(getSalary()).append(System.lineSeparator());
        info.append("Active:").append(isActive()).append(System.lineSeparator());
        info.append("Email:").append(getEmail()).append(System.lineSeparator());
        info.append("Password:").append(getPassword()).append(System.lineSeparator());
      return info.toString();
    }
}
