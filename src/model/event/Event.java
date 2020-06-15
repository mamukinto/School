package model.event;

import model.DBObject;
import utils.DateFormatsUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends DBObject implements Comparable<Event> {

    private String content;

    private LocalDateTime date;

    private String studentPersonalId;

    public Event() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStudentPersonalId() {
        return studentPersonalId;
    }

    public void setStudentPersonalId(String studentPersonalId) {
        this.studentPersonalId = studentPersonalId;
    }

    @Override
    public String getInfo() {
        StringBuilder stringBuilder = new StringBuilder();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DateFormatsUtils.DATE_TIME_FORMAT_FOR_FILE_NAME);
        String date = getDate().format(dateFormat);
        stringBuilder.append("Date:").append(date).append(System.lineSeparator());
        stringBuilder.append("StudentPersonalId:").append(studentPersonalId).append(System.lineSeparator());
        stringBuilder.append("Content:").append(getContent()).append(System.lineSeparator());
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Event event) {
        return event.getDate().compareTo(date);
    }
}
