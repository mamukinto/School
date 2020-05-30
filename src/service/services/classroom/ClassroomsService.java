package service.services.classroom;

import model.Classroom;
import model.Student;
import model.Teacher;
import model.exception.SchoolException;

import java.util.List;

public interface ClassroomsService {

    List<Classroom> getStandartClassrooms();

    List<Classroom> getClassrooms();

    List<Classroom> getClassroomsByTeacher(Teacher teacher);

    Classroom getClassroomByName(String classroomName);

    List<Student> getStudentsFromClassroom(Classroom classroom);

    void editClassroom(Classroom classroom) throws SchoolException;

    void addStandartClassrooms() throws SchoolException;

    void addClassroom(Classroom classroom) throws SchoolException;

    void removeClassroom(Classroom classroom) throws SchoolException;

    void updateClassrooms() throws SchoolException;

}
