package model.classrom;

import java.util.HashMap;

import model.DBObject;
import model.subject.Subject;
import model.user.teacher.Teacher;
import service.helpers.DBObjectFormatHelper;

public class Classroom extends DBObject {

    private String id;

    private String name;

    private HashMap<Subject, Teacher> teachers;

    private String subjectTeachersString;

    public Classroom() {}

    public Classroom(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Subject, Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(HashMap<Subject, Teacher> teachers) {
        this.teachers = teachers;
    }

    public String getSubjectTeachersString() {
        return subjectTeachersString;
    }

    public void setSubjectTeachersString(String subjectTeachersString) {
        this.subjectTeachersString = subjectTeachersString;
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
        result = 31 * result + id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getInfo() {
        return "Name:" + getName() + System.lineSeparator() +
                "Id:" + getId() + System.lineSeparator() +
                "Active:" + isActive() + System.lineSeparator() +
                getTeachersString(teachers);
    }

    private String getTeachersString(HashMap<Subject, Teacher> teachers) {
        StringBuilder teachersStringBuilder = new StringBuilder();
        teachers.forEach((subject, teacher) -> teachersStringBuilder.append(subject.getName())
                .append(DBObjectFormatHelper.MAP_EQUALLY)
                .append(teacher.getPersonalId())
                .append(DBObjectFormatHelper.MAP_SPLITTER));
       return teachersStringBuilder.substring(0,teachersStringBuilder.length() - 1);
    }
}
