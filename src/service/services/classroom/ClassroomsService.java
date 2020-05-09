package service.services.classroom;

import model.Classroom;
import model.exception.SchoolException;

import java.util.List;

public interface ClassroomsService {

    List<Classroom> getStandartClassrooms();

    List<Classroom> getClassrooms();

    Classroom getClassroomByName(String classroomName);

    void addStandartClassrooms() throws SchoolException;

    void removeClassroom(Classroom classroom) throws SchoolException;

    void updateClassrooms() throws SchoolException;

}
