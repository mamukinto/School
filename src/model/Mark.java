package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Mark extends DBObject implements Comparable<Mark>{

    private int value;

    private Date date;

    private String note;

    private Student student;

    private Teacher teacher;

    public Mark() {

    }

    public Mark(int value) {
        this.value = value;
    }

    public Mark(int value, Date date, String note) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH-mm-ss");
        String date = dateFormat.format(getDate());
        stringBuilder.append("Date:").append(date).append(System.lineSeparator());
        stringBuilder.append("Note:").append(getNote());
        stringBuilder.append("Active:").append(isActive());
        return stringBuilder.toString();
    }

}


