package dao;

import model.event.Event;
import model.exception.SchoolException;
import service.helpers.event.EventFormatHelper;
import utils.DateFormatsUtils;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DAOEvent implements DAOService<Event> {

    private final EventFormatHelper formatHelper = new EventFormatHelper();

    @Override
    public String write(Event dbObject) throws SchoolException {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DateFormatsUtils.DATE_TIME_FORMAT_FOR_FILE_NAME);
        String formattedEvent = formatHelper.format(dbObject);
        String src = "database/students/" + dbObject.getStudentPersonalId() + "/events/" + dbObject.getDate().format(dateFormat);

        File file = new File(src);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(formattedEvent);
        } catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }
        return "Succesfully writed to" + file.getPath();
    }

    @Override
    public Event read(String filePath) throws SchoolException {
        Event event;
        File eventFile = new File(filePath);

        try(BufferedReader br = new BufferedReader(new FileReader(eventFile))) {
            String strCurrentLine;
            StringBuilder subjectStringFromFile = new StringBuilder();
            while ((strCurrentLine = br.readLine()) != null) {
                subjectStringFromFile.append(strCurrentLine).append(System.lineSeparator());
            }

            event = ((Event) formatHelper.parse(subjectStringFromFile.toString()));
        } catch (IOException ex) {
            throw new SchoolException(ex.getMessage());
        }

        return event;
    }

    @Override
    public List<Event> readAll() throws SchoolException {
        return null;
    }

    public List<Event> readAllByStudent(String studentPersonalId) throws SchoolException{
        List<Event> events = new ArrayList<>();
        String src = "database/students/" + studentPersonalId + "/events/";
        File folder = new File(src);
        folder.mkdir();
        List<File> listOfFiles = new ArrayList<>(Arrays.asList(folder.listFiles()));
        for(File file: listOfFiles) {
            events.add(read(file.getAbsolutePath()));
        }
        return events;
    }
}
