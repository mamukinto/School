package service.services.classroom;

import dao.DAOClassroom;
import dao.DAOService;
import model.Classroom;
import model.exception.SchoolException;
import storage.Storage;

import java.util.List;

public class ClassroomsServiceImpl implements ClassroomsService {

    private final DAOService<Classroom> daoService = new DAOClassroom();

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
    public void addStandartClassrooms() throws SchoolException {
        daoService.writeAll(getStandartClassrooms());
    }

    @Override
    public void addClassroom(Classroom classroom) throws SchoolException {
        daoService.write(classroom);
    }

    @Override
    public void removeClassroom(Classroom classroom) throws SchoolException {
        classroom.setActive(false);
        daoService.write(classroom);
    }

    @Override
    public void updateClassrooms() throws SchoolException {
        Storage.classrooms = daoService.readAll();
    }
}
