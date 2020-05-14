package model;

public class Subject extends DBObject {
    private String name;
    private double costPerHour;

    public Subject() {
    }

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
        int result;
        result = name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name + "(" + costPerHour +"$)";
    }

    @Override
    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Name:").append(getName()).append(System.lineSeparator());
        info.append("CostPerHour:").append(getCostPerHour()).append(System.lineSeparator());
        info.append("Active:").append(isActive());
        return info.toString();
    }
}
