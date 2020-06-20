package service.services.teacher;

import dao.DAOService;
import dao.DAOTeacher;
import model.classrom.Classroom;
import model.event.Event;
import model.exception.SchoolException;
import model.mark.Mark;
import model.user.student.Student;
import model.user.student.StudentWeekView;
import model.user.teacher.Teacher;
import service.email.EmailSender;
import service.helpers.auth.PasswordGenerator;
import service.services.classroom.ClassroomsService;
import service.services.classroom.ClassroomsServiceImpl;
import service.services.event.EventService;
import service.services.event.EventServiceImpl;
import service.services.mark.MarkService;
import service.services.mark.MarkServiceImpl;
import storage.Storage;
import utils.DateFormatsUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    private static final MarkService markService = new MarkServiceImpl();

    private static final int INITIAL_PASSWORD_LENGTH = 8;

    private static final String WELCOME_EMAIL_FROM_ADDRESS = "Java.Mziuri@gmail.com";

    private static final String WELCOME_EMAIL_SUBJECT = "System Registration";

    private final DAOService<Teacher> daoService = new DAOTeacher();

    private final ClassroomsService classroomsService = new ClassroomsServiceImpl();

    private final EventService eventService = new EventServiceImpl();


    @Override
    public void addTeacher(Teacher teacher) throws SchoolException {
        String password = PasswordGenerator.generatePassword(INITIAL_PASSWORD_LENGTH);
        teacher.setPassword("" + password.hashCode());
        daoService.write(teacher);
        try {
            EmailSender.sendEmail(WELCOME_EMAIL_FROM_ADDRESS, teacher.getEmail(), WELCOME_EMAIL_SUBJECT, getEmailMessage(teacher.getFirstName(), password));
        } catch (SchoolException e) {
            System.out.println("Cant send email -> " + e.getMessage());
        }
        updateTeachers();
    }

    @Override
    public void editTeacher(Teacher teacher) throws SchoolException {
        daoService.write(teacher);
        updateTeachers();
    }

    @Override
    public List<Teacher> getTeachers() {
        return Storage.teachers;
    }

    @Override
    public Teacher getTeacherByPersonalId(String personalId) {
        for (Teacher teacher : Storage.teachers) {
            if (teacher.getPersonalId().equals(personalId)) {
                return teacher;
            }
        }
        return null;
    }

    @Override
    public List<Teacher> getTeachersBySubject(String subjectName) {
        List<Teacher> teachersOfThisSubject = new ArrayList<>();
        getTeachers().forEach(teacher -> {
            if (teacher.getSubject().getName().equals(subjectName)) {
                teachersOfThisSubject.add(teacher);
            }
        });
        return teachersOfThisSubject;
    }

    @Override
    public List<StudentWeekView> getTeachersStudentWeekViews(Teacher teacher, Classroom classroom, LocalDate from) {
        List<StudentWeekView> studentWeekViewList = new ArrayList<>();
        List<Student> students = classroomsService.getStudentsFromClassroom(classroom);
        students.forEach(student -> {
            StudentWeekView tempStudentWeekView = new StudentWeekView();
            tempStudentWeekView.setName(student.getFirstName() + " " + student.getLastName());
            tempStudentWeekView.setMondayMark(getMarkValueByDate(student.getJournal().get(teacher.getSubject()), from));
            tempStudentWeekView.setTuesdayMark(getMarkValueByDate(student.getJournal().get(teacher.getSubject()), from.plusDays(1)));
            tempStudentWeekView.setWednesdayMark(getMarkValueByDate(student.getJournal().get(teacher.getSubject()), from.plusDays(2)));
            tempStudentWeekView.setThursdayMark(getMarkValueByDate(student.getJournal().get(teacher.getSubject()), from.plusDays(3)));
            tempStudentWeekView.setFridayMark(getMarkValueByDate(student.getJournal().get(teacher.getSubject()), from.plusDays(4)));
            tempStudentWeekView.setPersonalId(student.getPersonalId());
            studentWeekViewList.add(tempStudentWeekView);
        });
        return studentWeekViewList;
    }

    @Override
    public void addMarkToStudent(Teacher teacher, Mark mark, Student student, LocalDate date) throws SchoolException {
        markService.addMarkToStudent(student, teacher, mark, date);
        markService.updateJournal(student);
        addMarkEventForStudent(mark,student,teacher,date);
    }

    @Override
    public void removeTeacher(Teacher teacher) throws SchoolException {
        teacher.setActive(false);
        daoService.write(teacher);
        updateTeachers();
    }

    @Override
    public void updateTeachers() throws SchoolException {
        Storage.teachers = daoService.readAll();
    }

    private String getEmailMessage(String firstName, String password) {
        return firstName + ", your temporary password is " + password;
    }

    public String getMarkValueByDate(List<Mark> marks,LocalDate date) {
        if  (marks != null) {
            for (Mark mark : marks) {
                if (mark.getDate().equals(date)) {
                    return "" + mark.getValue();
                }
            }
        }
        return "";
    }

    private void addMarkEventForStudent(Mark mark, Student student, Teacher teacher, LocalDate date) throws SchoolException {
        Event event = new Event();
        event.setContent(getContentForEvent(teacher.getFirstName() + " " + teacher.getLastName(),teacher.getSubject().getName(),mark.getValue(),mark.getDate()));
        event.setDate(LocalDateTime.now());
        event.setStudentPersonalId(student.getPersonalId());
        eventService.updateEventsOfStudent(student);
        eventService.addEvent(event,student);
    }

    private String getContentForEvent(String teacherName, String subjectName, int markValue, LocalDate markDate) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DateFormatsUtils.DATE_FORMAT);
        return String.format("%s(%s) wrote mark %s to you for date %s",teacherName,subjectName,markValue,markDate.format(dateFormat));
    }

}
