package model;

public class Classroom extends DBObject {
    private String name;
    private int id;

    public Classroom() {
    }

    public Classroom(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classroom classroom = (Classroom) o;

        if (id != classroom.id) return false;
        return name.equals(classroom.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Name:").append(getName()).append(System.lineSeparator());
        info.append("Id:").append(getId()).append(System.lineSeparator());
        info.append("Active:").append(isActive());
        return info.toString();
    }
}
