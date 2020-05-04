package model;

import java.util.List;

public class Teacher extends Human {
    private Subject subject;
    private double salary;
    private List<Classroom> classrooms;

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
        salary = classrooms.size()*subject.getCostPerHour()*3;
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
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

        if(getClassrooms() != null) {
            info.append("Classrooms:");
            for (int i = 0; i < getClassrooms().size(); i++) {
                if (i + 1 == getClassrooms().size()) {
                    info.append(getClassrooms().get(i).getName());
                } else {
                    info.append(getClassrooms().get(i).getName()).append(",");
                }
            }
        }
        info.append(System.lineSeparator());
        info.append("Email:").append(getEmail()).append(System.lineSeparator());
        info.append("Password:").append(getPassword()).append(System.lineSeparator());
      return info.toString();
    }
}
