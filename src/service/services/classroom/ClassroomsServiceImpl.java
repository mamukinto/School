package service.services.classroom;

import dao.DAOClassroom;
import dao.DAOService;
import model.Classroom;
import model.Subject;
import model.Teacher;
import model.exception.SchoolException;
import service.services.teacher.TeacherService;
import service.services.teacher.TeacherServiceImpl;
import storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassroomsServiceImpl implements ClassroomsService {

    private final DAOService<Classroom> daoService = new DAOClassroom();

    private final TeacherService teacherService = new TeacherServiceImpl();

    @Override
    public List<Classroom> getStandartClassrooms() {
        return Storage.standartClassrooms;
    }

    @Override
    public List<Classroom> getClassrooms() {
        return Storage.classrooms;
    }

    @Override
    public List<Classroom> getClassroomsByTeacher(Teacher teacher) {
        List<Classroom> classrooms = new ArrayList<>();
        getClassrooms().forEach(classroom -> {
            if (classroom.getTeachers().containsValue(teacher)) {
                classrooms.add(classroom);
            }
        });
        return classrooms;
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
    public void editClassroom(Classroom classroom) throws SchoolException {
        daoService.write(classroom);
    }

    @Override
    public void addStandartClassrooms() throws SchoolException {
        daoService.writeAll(getStandartClassrooms());
        updateClassrooms();
    }

    @Override
    public void addClassroom(Classroom classroom) throws SchoolException {
        daoService.write(classroom);
        updateClassrooms();
    }

    @Override
    public void removeClassroom(Classroom classroom) throws SchoolException {
        classroom.setActive(false);
        daoService.write(classroom);
        updateClassrooms();
    }

    @Override
    public void updateClassrooms() throws SchoolException {
        Storage.classrooms = daoService.readAll();
    }
}
