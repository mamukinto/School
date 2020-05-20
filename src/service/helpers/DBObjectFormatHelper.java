package service.helpers;

import model.DBObject;

public interface DBObjectFormatHelper {

    String LINE_SPLITTER = System.lineSeparator();
    String INFO_SPLITTER = ":";
    String MAP_SPLITTER = ",";
    String MAP_EQUALLY = "=";

    DBObject parse(String dBObjectString);

    String format(DBObject dbObject);
}
