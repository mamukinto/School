package model.mark;

import model.DBObject;
import model.user.student.Student;
import model.user.teacher.Teacher;
import utils.DateFormatsUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Mark extends DBObject implements Comparable<Mark>{

    private int value;

    private LocalDate date;

    private String note;

    private Student student;

    private Teacher teacher;

    public Mark() {}

    public Mark(int value) {
        this.value = value;
    }

    public Mark(int value, LocalDate date, String note) {
        this.value = value;
        this.date = date;
        this.note = note;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "{" +
                "value=" + value +
                ", date=" + date +
                ", note='" + note + '\'' +
                '}';
    }

    @Override
    public int compareTo(Mark mark) {
        return Double.compare(this.getValue(),mark.getValue());
    }

    @Override
    public String getInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Value:").append(getValue()).append(System.lineSeparator());

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DateFormatsUtils.DATE_FORMAT);
        String date = getDate().format(dateFormat);
        stringBuilder.append("Date:").append(date).append(System.lineSeparator());
        stringBuilder.append("Note:").append(getNote()).append(System.lineSeparator());
        return stringBuilder.toString();
    }
}


