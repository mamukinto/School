package model.subject;

import model.DBObject;

public class Subject extends DBObject {
    private String name;
    private double costPerHour;

    public Subject() {}

    public Subject(String name, double costPerHour) {
        this.name = name;
        this.costPerHour = costPerHour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(double costPerHour) {
        this.costPerHour = costPerHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (Double.compare(subject.costPerHour, costPerHour) != 0) return false;
        return name.equals(subject.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name + "(" + costPerHour +"$)";
    }

    @Override
    public String getInfo() {
        return "Name:" + getName() + System.lineSeparator() +
                "CostPerHour:" + getCostPerHour() + System.lineSeparator() +
                "Active:" + isActive();
    }
}
