package service.services.classroom;

import dao.DAOClassroom;
import model.Classroom;
import model.exception.SchoolException;
import service.services.StorageService;
import storage.Storage;

import java.util.List;

public class ClassroomsServiceImpl implements ClassroomsService {
    @Override
    public List<Classroom> getStandartClassrooms() {
        return Storage.standartClassrooms;
    }

    @Override
    public List<Classroom> getClassrooms() {
        return Storage.classrooms;
    }

    @Override
    public Classroom getClassroomByName(String classroomName) {
        Classroom classroom = new Classroom();
        List<Classroom> classrooms = Storage.classrooms;
        for (Classroom value : classrooms) {
            if (value.getName().equals(classroomName)) {
                classroom = value;
            }
        }
        return classroom;
    }

    @Override
    public void addStandartClassrooms() {
        DAOClassroom.writeAll(getStandartClassrooms());
    }

    @Override
    public void removeClassroom(Classroom classroom) throws SchoolException {
        classroom.setActive(false);
        DAOClassroom.write(classroom);
    }

    @Override
    public void updateClassrooms() throws SchoolException {
        Storage.classrooms = DAOClassroom.readAll();
    }
}
