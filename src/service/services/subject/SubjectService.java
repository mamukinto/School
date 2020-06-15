package service.services.subject;

import model.subject.Subject;
import model.exception.SchoolException;

import java.util.List;

public interface SubjectService {

    List<Subject> getStandartSubjects();

    List<Subject> getSubjects();

    Subject getSubjectByName(String subjectName);

    void editSubject(Subject subject) throws SchoolException;

    void addSubject(Subject subject) throws SchoolException;

    void addStandartSubjects() throws SchoolException;

    void removeSubject(Subject subject) throws SchoolException;

    void updateSubjects() throws SchoolException;

}
