package service.services.subject;

import dao.DAOService;
import dao.DAOSubject;
import model.subject.Subject;
import model.exception.SchoolException;
import storage.Storage;

import java.util.List;

public class SubjectServiceImpl implements SubjectService {

    private final DAOService<Subject> daoService = new DAOSubject();

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
    public void editSubject(Subject subject) throws SchoolException {
        daoService.write(subject);
    }

    @Override
    public void addSubject(Subject subject) throws SchoolException {
        daoService.write(subject);
        updateSubjects();
    }


    @Override
    public void removeSubject(Subject subject) throws SchoolException {
        subject.setActive(false);
        daoService.write(subject);
        updateSubjects();
    }

    @Override
    public void updateSubjects() throws SchoolException {
        Storage.subjects = daoService.readAll();
    }
}
