package service.services.student;

import dao.DAOService;
import dao.DAOStudent;
import model.mark.Mark;
import model.user.student.Student;
import model.subject.Subject;
import model.exception.SchoolException;
import model.user.student.SubjectWeekViewForStudent;
import service.email.EmailSender;
import service.helpers.auth.PasswordGenerator;
import service.services.teacher.TeacherServiceImpl;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    private static final int INITIAL_PASSWORD_LENGTH = 8;

    private static final String WELCOME_EMAIL_FROM_ADDRESS = "Java.Mziuri@gmail.com";

    private static final String WELCOME_EMAIL_SUBJECT = "System Registration";

    private final DAOService<Student> daoService = new DAOStudent();

    private static TeacherServiceImpl teacherService = new TeacherServiceImpl();

    @Override
    public void addStudent(Student student) throws SchoolException {
        String password = PasswordGenerator.generatePassword(INITIAL_PASSWORD_LENGTH);
        student.setPassword("" + password.hashCode());
        daoService.write(student);
        try {
            EmailSender.sendEmail(WELCOME_EMAIL_FROM_ADDRESS, student.getEmail(), WELCOME_EMAIL_SUBJECT, getEmailMessage(student.getFirstName(), password));
        } catch (SchoolException e) {
            System.out.println(e.getMessage());
        }
        updateStudents();
    }

    @Override
    public void editStudent(Student student) throws SchoolException {
        daoService.write(student);
    }

    @Override
    public Student getStudentById(String personalId) throws SchoolException {
        for (Student student : Storage.students) {
            if (student.getPersonalId().equals(personalId)) {
                return student;
            }
        }
        return null;
    }

    @Override
    public List<Student> getStudents() {
        return Storage.students;
    }

    @Override
    public void updateStudents() throws SchoolException {
        Storage.students = daoService.readAll();
    }

    @Override
    public void removeStudent(Student student) throws SchoolException {
        student.setActive(false);
        daoService.write(student);
        updateStudents();
    }

    @Override
    public List<Mark> getMarksBySubject(Subject subject, Student student) {
        return student.getJournal().get(subject);
    }

    @Override
    public float getAverageMarkOfStudentBySubject(Student student, Subject subject) {
        List<Mark> marks = getMarksBySubject(subject, student);
        if (marks == null) {
            return 0;
        }
        List<Integer> values = new ArrayList<>();
        marks.forEach(mark -> values.add(mark.getValue()));
        float sum = 0;
        for (Integer value : values) {
           sum += value;
        }
        return sum/values.size();
    }

    @Override
    public List<SubjectWeekViewForStudent> getSubjectWeekViewForStudent(Student student, LocalDate from) {
        List<SubjectWeekViewForStudent> swvfs = new ArrayList<>();
        List<Subject> subjects = new ArrayList<>();
        student.getClassroom().getTeachers().forEach((subject, teacher) -> {
            subjects.add(subject);
        });

        subjects.forEach(subject -> {
            SubjectWeekViewForStudent tempWeekView =  new SubjectWeekViewForStudent();
            tempWeekView.setSubjectName(subject.getName());
            tempWeekView.setMondayMark(teacherService.getMarkValueByDate(student.getJournal().get(subject), from));
            tempWeekView.setTuesdayMark(teacherService.getMarkValueByDate(student.getJournal().get(subject), from.plusDays(1)));
            tempWeekView.setWednesdayMark(teacherService.getMarkValueByDate(student.getJournal().get(subject), from.plusDays(2)));
            tempWeekView.setThursdayMark(teacherService.getMarkValueByDate(student.getJournal().get(subject), from.plusDays(3)));
            tempWeekView.setFridayMark(teacherService.getMarkValueByDate(student.getJournal().get(subject), from.plusDays(4)));
            swvfs.add(tempWeekView);
        });



        return swvfs;
    }

    private String getEmailMessage(String firstName, String password) {
        return firstName + ", your temporary password is " + password;
    }
}
