package service.helpers.classroom;

import model.Classroom;
import model.DBObject;
import service.helpers.DBObjectFormatHelper;

public class ClassroomFormatHelper implements DBObjectFormatHelper {

    @Override
    public DBObject parse(String DBObjectString) {
        Classroom classroom = new Classroom();
        String[] classroomLines = DBObjectString.split(LINE_SPLITTER);
        classroom.setName(classroomLines[0].split(INFO_SPLITTER)[1]);
        classroom.setId(Integer.parseInt(classroomLines[1].split(INFO_SPLITTER)[1]));
        classroom.setActive(Boolean.parseBoolean(classroomLines[2].split(INFO_SPLITTER)[1].trim()));
        return classroom;
    }

    @Override
    public String format(DBObject dbObject) {
        return ((Classroom) dbObject).getInfo();
    }
}
