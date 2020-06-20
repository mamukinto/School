package service.helpers.event;

import model.DBObject;
import model.event.Event;
import service.helpers.DBObjectFormatHelper;
import utils.DateFormatsUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventFormatHelper implements DBObjectFormatHelper {

    @Override
    public DBObject parse(String dBObjectString) {
        Event event = new Event();
        String[] eventLines = dBObjectString.split(LINE_SPLITTER);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DateFormatsUtils.DATE_TIME_FORMAT_FOR_DB);
        event.setDate(LocalDateTime.parse(eventLines[0].split(INFO_SPLITTER)[1], dateFormat));
        event.setStudentPersonalId(eventLines[1].split(INFO_SPLITTER)[1].trim());
        event.setContent(eventLines[2].split(INFO_SPLITTER)[1].trim());
        return event;
    }

    @Override
    public String format(DBObject dbObject) {
        return dbObject.getInfo();
    }
}
