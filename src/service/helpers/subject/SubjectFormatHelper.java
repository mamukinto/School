package service.helpers.subject;

import model.DBObject;
import model.Subject;
import service.helpers.DBObjectFormatHelper;
import storage.Storage;

import java.util.List;

public class SubjectFormatHelper implements DBObjectFormatHelper {

    @Override
    public DBObject parse(String dBObjectString) {
        Subject subject = new Subject();
        String[] subjectLines = dBObjectString.split(LINE_SPLITTER);
        subject.setName(subjectLines[0].split(INFO_SPLITTER)[1]);
        subject.setCostPerHour(Double.parseDouble(subjectLines[1].split(INFO_SPLITTER)[1]));
        subject.setActive(Boolean.parseBoolean(subjectLines[2].split(INFO_SPLITTER)[1].trim()));
        return subject;
    }

    @Override
    public String format(DBObject dbObject) {
        Subject subject = (Subject) dbObject;
        return subject.getInfo();
    }

    public static Subject getSubjectByName(String subjectName) {
        List<Subject> subjects = Storage.subjects;
        Subject subject = new Subject();
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getName().equals(subjectName)) {
                subject = subjects.get(i);
            }
        }
        return subject;
    }
}
