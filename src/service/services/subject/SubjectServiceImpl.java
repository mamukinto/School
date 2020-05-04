package service.services.subject;

import dao.DAOStudent;
import dao.DAOSubject;
import model.Subject;
import model.exception.SchoolException;
import service.services.StorageService;
import storage.Storage;

import java.util.List;

public class SubjectServiceImpl implements SubjectService {
    @Override
    public List<Subject> getStandartSubjects() {
        return Storage.standartSubjects;
    }

    @Override
    public List<Subject> getSubjects() {
        return Storage.subjects;
    }

    @Override
    public Subject getSubjectByName(String subjectName) {
        Subject subject = new Subject();
        List<Subject> subjects = Storage.subjects;
        for (Subject value : subjects) {
            if (value.getName().equals(subjectName)) {
                subject = value;
            }
        }
        return subject;
    }

    @Override
    public void addStandartSubjects() {
        DAOSubject.writeAll(getStandartSubjects());
    }

    @Override
    public void removeSubject(Subject subject) throws SchoolException {
        subject.setActive(false);
        DAOSubject.write(subject);
    }

    @Override
    public void updateSubjects() throws SchoolException {
        Storage.subjects = DAOSubject.readAll();
    }
}
