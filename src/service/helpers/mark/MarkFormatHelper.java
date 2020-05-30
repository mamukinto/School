package service.helpers.mark;

import model.DBObject;
import model.Mark;
import service.helpers.DBObjectFormatHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MarkFormatHelper implements DBObjectFormatHelper {

    @Override
    public DBObject parse(String dBObjectString) {
        Mark mark = new Mark();
        String[] markLines = dBObjectString.split(LINE_SPLITTER);
        mark.setValue(Integer.parseInt(markLines[0].split(INFO_SPLITTER)[1].trim()));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH-mm-ss");

        mark.setDate(LocalDate.parse(markLines[1].split(INFO_SPLITTER)[1], dateFormat));

        mark.setNote(markLines[2].split(INFO_SPLITTER)[1].trim());
        mark.setActive(Boolean.parseBoolean(markLines[3].split(INFO_SPLITTER)[1].trim()));
        return mark;
    }

    @Override
    public String format(DBObject dbObject) {
        Mark mark = (Mark) dbObject;
        return mark.getInfo();
    }
}
