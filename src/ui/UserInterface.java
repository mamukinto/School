package ui;

import dao.DAOClassroom;
import dao.DAOSubject;
import model.*;
import model.exception.SchoolException;
import service.services.auth.AuthService;
import service.services.auth.AuthServiceImpl;
import service.services.classroom.ClassroomsService;
import service.services.classroom.ClassroomsServiceImpl;
import service.services.mark.MarkService;
import service.services.mark.MarkServiceImpl;
import service.services.student.StudentService;
import service.services.student.StudentServiceImpl;
import service.services.subject.SubjectService;
import service.services.subject.SubjectServiceImpl;
import service.services.teacher.TeacherService;
import service.services.teacher.TeacherServiceImpl;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class UserInterface {
    private static Scanner scanner = new Scanner(System.in);
    private static Teacher loggedInTeacher;
    private static Student loggedInStudent;

    private static final TeacherService teacherService = new TeacherServiceImpl();

    private static final ClassroomsService classroomsService = new ClassroomsServiceImpl();

    private static final SubjectService subjectService = new SubjectServiceImpl();

    private static final StudentService studentService = new StudentServiceImpl();

    private static final MarkService markService = new MarkServiceImpl();

    private static final AuthService authService = new AuthServiceImpl();

    private static Map<Integer, Student> studentsMap = new HashMap<>();

    private static void enterSystemPanel() {
        System.out.println("Enter system as: ");
        System.out.println("1. Director");
        System.out.println("2. Teacher");
        System.out.println("3. Student");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                directorPanel();
                break;
            case "2":
                teacherLogIn();
                teacherPanel();
                break;
            case "3":
                studentLogIn();
                studentPanel();
                break;
            default:
                System.out.println("Not such option as " + choice);
                enterSystemPanel();
                break;

        }
    }

    private static void directorPanel() {
        System.out.println("Choose options by entering relevant number below");
        System.out.println("1. Add classroom");
        System.out.println("2. Remove classroom");
        System.out.println("3. View all classrooms");
        System.out.println("4. Add subject");
        System.out.println("5. Remove subject");
        System.out.println("6. View all my subjects");

        if (!classroomsService.getClassrooms().isEmpty()) {
            System.out.println("7. Add student");
            if (!studentService.getStudents().isEmpty()) {
                System.out.println("8. Remove student");
                System.out.println("9. View all students");
            }
            if (!subjectService.getSubjects().isEmpty()) {
                System.out.println("10. Add teacher");
                if (!teacherService.getTeachers().isEmpty()) {
                    System.out.println("11. Remove teacher");
                    System.out.println("12. View all teachers");
                }
            }
        }

        System.out.println("0. Go back");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                addClassroom();
                break;
            case "2":
                removeClassroom();
                directorPanel();
                break;
            case "3":
                showAllClassrooms();
            case "4":
                addSubject();
            case "5":
                removeSubject();
                directorPanel();
                break;
            case "6":
                showAllSubjects();
                break;
            case "7":
                addStudent();
                break;
            case "8":
                removeStudent();
                directorPanel();
                break;
            case "9":
                showAllStudents();
            case "10":
                addTeacher();
            case "11":
                removeTeacher();
                directorPanel();
            case "12":
                showAllTeachers();
            case "0":
                enterSystemPanel();
                break;
            default:
                System.out.println("Not such option as " + choice);
                directorPanel();
                break;
        }
    }

    private static String[] addHuman() {
        String[] humanInfo = new String[4];
        int index = 0;
        System.out.print("First name:");
        humanInfo[index++] = scanner.nextLine();
        System.out.print("Last name:");
        humanInfo[index++] = scanner.nextLine();
        System.out.print("Personal Id:");
        humanInfo[index++] = scanner.nextLine();
        System.out.println("Email:");
        humanInfo[index++] = scanner.nextLine();
        return humanInfo;
    }

    private static void addStudent() {
        System.out.println("To add new student enter next information");

        Student student = new Student();
        String[] userInfo = addHuman();
        int index = 0;
        student.setFirstName(userInfo[index++]);
        student.setLastName(userInfo[index++]);
        student.setPersonalId(userInfo[index++]);
        student.setEmail(userInfo[index++]);
        student.setClassroom(chooseClassroomForStudent());


        try {
            studentService.addStudent(student);
        } catch (SchoolException ex) {
            System.out.println("Unexpected exception : " + ex.getMessage() + System.lineSeparator() + "Try again");
            addStudent();
        }

        try {
            studentService.updateStudents();
        } catch (SchoolException e) {
            System.out.println("Cant update storage while adding student - " + e.getMessage());
        }


        System.out.println("Student " + student.getFirstName() + " successfully added!");
        directorPanel();
    }

    private static Classroom chooseClassroomForStudent() {
        System.out.println("Enter classroom name: ");
        String classroomName = scanner.nextLine();
        return classroomsService.getClassroomByName(classroomName);
    }

    private static void showAllStudents(){

        try {
            studentService.updateStudents();
        } catch (SchoolException ex) {
            System.out.println("Unexpected exception : " + ex.getMessage());
            directorPanel();
        }
        List<Student> students = studentService.getStudents();
        if (students.size() == 0) {
            System.out.println("Nothing to see here  :)");
        }
        else {
            students.forEach((s) -> System.out.println(s.getInfo() + System.lineSeparator()  + "-------------------------------------------------"));
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
        directorPanel();
    }

    private static void addTeacher() {
        System.out.println("To add new teacher enter next information");

        Teacher teacher = new Teacher();
        String[] nameAndLastNameAndPersonalId = addHuman();
        teacher.setFirstName(nameAndLastNameAndPersonalId[0]);
        teacher.setLastName(nameAndLastNameAndPersonalId[1]);
        teacher.setPersonalId(nameAndLastNameAndPersonalId[2]);
        teacher.setEmail(nameAndLastNameAndPersonalId[3]);
        teacher.setSubject(chooseSubjectForTeacher());
        teacher.setClassrooms(chooseClassroomsForTeacher());

        try {
            teacherService.addTeacher(teacher);
        } catch (SchoolException ex) {
            System.out.println("Unexpected exception : " + ex.getMessage() + System.lineSeparator() + "Try again");
            addTeacher();
        }

        try {
            teacherService.updateTeachers();
        } catch (SchoolException e) {
            System.out.println("Cant update storage while adding teacher - " + e.getMessage());
        }


        System.out.println("Teacher " + teacher.getFirstName() + " successfully added!");
        directorPanel();

    }

    private static List<Classroom> chooseClassroomsForTeacher() {
        List<Classroom> chosenClassrooms = new ArrayList<>();
        while(true) {
            System.out.println("Enter in which classroom (name) will this teacher teach (1-12 (standart classroom pack names)) or 0 to continue");
            String choice = scanner.nextLine();
            if (choice.equals("0")) {
                return chosenClassrooms;
            }
            else {
                chosenClassrooms.add(classroomsService.getClassroomByName(choice));
            }
        }
    }

    private static Subject chooseSubjectForTeacher() {
        System.out.println("Enter subject which this teacher will teach from below by entering relevant number");
        for (int i = 0; i < subjectService.getSubjects().size(); i++) {
            System.out.println(i+1 + ". " + subjectService.getSubjects().get(i).getName());
        }
        return subjectService.getSubjects().get(Integer.parseInt(scanner.nextLine()) - 1);
    }


    private static void showAllTeachers() {
        try {
            teacherService.updateTeachers();
        } catch (SchoolException ex) {
            System.out.println("Unexpected exception : " + ex.getMessage());
            directorPanel();
        }
        List<Teacher> teachers = teacherService.getTeachers();
        if (teachers.size() == 0) {
            System.out.println("Nothing to see here  :)");
        }
        else {
            teachers.forEach((teacher) -> System.out.println(teacher.getInfo() + System.lineSeparator()  + "-------------------------------------------------"));
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
        directorPanel();
    }

    private static void addClassroom() {

        if (classroomsService.getClassrooms().size() == 0) {
            System.out.println("Do you want to add standart (from 1 to 12) classrooms pack?");
            System.out.println("1. Yes");
            System.out.println("2. No i will add it myself");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                classroomsService.addStandartClassrooms();
                System.out.println("Added standart classrooms pack!");
                try {
                    classroomsService.updateClassrooms();
                } catch (SchoolException e) {
                    System.out.println("Cant update storage while adding classrooms - " + e.getMessage());
                }
                directorPanel();
            }
            else {
                System.out.println("To add new classroom enter next information");
                System.out.print("Classroom name: ");
                String name = scanner.nextLine();
                System.out.print("Classroom id: ");
                int id = Integer.parseInt(scanner.nextLine());

                Classroom classroom = new Classroom(name, id);

                try {
                    DAOClassroom.write(classroom);
                } catch (SchoolException ex) {
                    System.out.println("Unexpected exception : " + ex.getMessage() + System.lineSeparator() + "Try again");
                    addClassroom();
                }
                System.out.println("Classroom " + classroom.getName() + " successfully added!");
                try {
                    classroomsService.updateClassrooms();
                } catch (SchoolException e) {
                    System.out.println("Cant update storage from classroom adding - " + e.getMessage());
                }
                directorPanel();
            }
        }
        else {

            System.out.println("To add new classroom enter next information");
            System.out.print("Classroom name: ");
            String name = scanner.nextLine();
            System.out.print("Classroom id: ");
            int id = Integer.parseInt(scanner.nextLine());

            Classroom classroom = new Classroom(name, id);

            try {
                DAOClassroom.write(classroom);
            } catch (SchoolException ex) {
                System.out.println("Unexpected exception : " + ex.getMessage() + System.lineSeparator() + "Try again");
                addClassroom();
            }
            System.out.println("Classroom " + classroom.getName() + " successfully added!");
            directorPanel();
        }
    }

    private static void showAllClassrooms() {
        try {
            classroomsService.updateClassrooms();
        } catch (SchoolException ex) {
            System.out.println("Unexpected exception : " + ex.getMessage());
            directorPanel();
        }
        List<Classroom> classrooms = classroomsService.getClassrooms();
        if (classrooms.size() == 0) {
            System.out.println("Nothing to see here  :)");
        }
        else {
            classrooms.forEach((classroom) -> System.out.println(classroom.getInfo() + System.lineSeparator()  + "-------------------------------------------------"));
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
        directorPanel();
    }

    private static void addSubject() {
        if (subjectService.getSubjects().size() == 0) {
            System.out.println("Do you want to add standart pack of subjects?");
            subjectService.getStandartSubjects().forEach((s) -> System.out.println(s.getInfo()));
            System.out.println("1. Yes");
            System.out.println("2. No i will add it myself");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                subjectService.addStandartSubjects();
                System.out.println("Added standart subjects pack!");
                try {
                    subjectService.updateSubjects();
                } catch (SchoolException e) {
                    System.out.println("Cant update storage from subject adding - " + e.getMessage());
                }
                directorPanel();
            }
            else {
                System.out.println("To add new subject enter next information");
                System.out.print("Subject name: ");
                String name = scanner.nextLine();
                System.out.print("Subject cost per hour: ");
                double costPerHour = Double.parseDouble(scanner.nextLine());
                Subject subject = new Subject(name,costPerHour);

                try {
                    DAOSubject.write(subject);
                } catch (SchoolException ex) {
                    System.out.println("Unexpected exception : " + ex.getMessage() + System.lineSeparator() + "Try again");
                    addSubject();
                }
                System.out.println("Subject " + subject.getName() + " successfully added!");
                try {
                    subjectService.updateSubjects();
                } catch (SchoolException e) {
                    System.out.println("Cant update storage from subject adding - " + e.getMessage());
                }

                directorPanel();
            }
        }
        System.out.println("To add new subject enter next information");
        System.out.print("Subject name: ");
        String name = scanner.nextLine();
        System.out.print("Subject cost per hour: ");
        double costPerHour = Double.parseDouble(scanner.nextLine());
        Subject subject = new Subject(name,costPerHour);

        try {
            DAOSubject.write(subject);
        } catch (SchoolException ex) {
            System.out.println("Unexpected exception : " + ex.getMessage() + System.lineSeparator() + "Try again");
            addSubject();
        }
        System.out.println("Subject " + subject.getName() + " successfully added!");
        directorPanel();

    }

    private static void showAllSubjects() {
        try {
            subjectService.updateSubjects();
        } catch (SchoolException ex) {
            System.out.println("Unexpected exception : " + ex.getMessage());
            directorPanel();
        }
        List<Subject> subjects = subjectService.getSubjects();
        if (subjects.size() == 0) {
            System.out.println("Nothing to see here  :)");
        }
        else {
            subjects.forEach((subject) -> System.out.println(subject.getInfo() + System.lineSeparator()  + "-------------------------------------------------"));
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
        directorPanel();
    }


    private static void teacherLogIn() {
        System.out.println("Enter your personal ID:");
        String personalId = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        try {
            Human human = authService.auth(personalId,password,UserType.TEACHER);
            if (human != null) {
                loggedInTeacher = (Teacher) human;
            } else {
                System.out.println("Your ID or password is incorrect");
                teacherLogIn();
            }
        } catch (SchoolException e) {
            System.out.println("Unexpected problem - " + e.getMessage());
        }
    }

    private static void showTeachersForLoggingIn() {
        int i = 0;
        for (Teacher teacher : teacherService.getTeachers()) {
            i++;
            System.out.println(i + ". " + teacher.getFirstName() + " " + teacher.getLastName());
        }
    }


    private static void teacherPanel() {
        System.out.println("Choose from options below by entering relevant number");
        System.out.println("1. View my students");
        System.out.println("2. Add mark to student");
        System.out.println("3. View my salary");
        System.out.println("4. View my classrooms");
        System.out.println("0. Go back");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                showTeacherStudents();
                break;
            case "2":
                addMarkToStudent();
                break;
            case "3":
                viewMySalary();
                break;
            case "4":
                viewMyClassrooms();
                break;
            case "0":
                enterSystemPanel();
                break;
            default:
                System.out.println("There is no option like " + choice);
                teacherPanel();
                break;
        }
    }

    private static void showTeacherStudents() {
        showTeacherStudentsInner();
        System.out.println("Press enter to continue....");
        scanner.nextLine();
        teacherPanel();
    }

    private static void showTeacherStudentsInner() {
        List<Student> students = teacherService.getTeacherStudents(loggedInTeacher);
        studentsMap = new HashMap<>();
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            System.out.println((i + 1) + ". " + student.getFirstName() + " " + student.getLastName());
            studentsMap.put(i, student);
        }
    }

    private static void viewMySalary() {
        System.out.println("Your salary : " + loggedInTeacher.getSalary());
        System.out.println("Press enter to continue...");
        scanner.nextLine();
        teacherPanel();
    }
    private static void viewMyClassrooms() {
        System.out.println("Your classrooms:");
        loggedInTeacher.getClassrooms().forEach((s -> {
            System.out.println(s.getName() + ",");
        }));
        System.out.println("Press enter to continue...");
        scanner.nextLine();
        teacherPanel();
    }

    private static void addMarkToStudent() {
        System.out.println("Choose who you want to add mark to by entering relevant number");
        showTeacherStudentsInner();
        Student chosenStudent = studentsMap.get(Integer.parseInt(scanner.nextLine()) - 1);
        System.out.print("Enter mark value (1-10): ");
        Mark mark = new Mark(Integer.parseInt(scanner.nextLine()));
        System.out.println("Enter note if u want:");
        mark.setNote(scanner.nextLine());

        try {
            teacherService.addMarkToStudent(loggedInTeacher,mark,chosenStudent);
        } catch (SchoolException e) {
            System.out.println("Cant add mark to the student - " + e.getMessage());
        }
        teacherPanel();
    }

    private static void studentLogIn() {
        System.out.println("Enter your personal ID:");
        String personalId = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        try {
            Human human = (Student) authService.auth(personalId,password,UserType.STUDENT);
            if (human != null) {
                loggedInStudent = (Student) human;
            } else {
                System.out.println("Your ID or password is incorrect");
                studentLogIn();
            }
        } catch (SchoolException e) {
            System.out.println("Unexpected problem - " + e.getMessage());
        }
    }

    private static void studentPanel() {
        System.out.println("Choose option by entering relevant number below:");
        System.out.println("1. View my marks");
        System.out.println("2. My personal info");
        System.out.println("3. View my average marks");
        System.out.println("0. Go back");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                viewStudentMarks();
                break;
            case "2":
                personalInfoOfStudent();
                break;
            case "3":
                viewStudentAverageMarks();
            case "0":
                enterSystemPanel();
                break;
            default:
                System.out.println("There is no such option as - " + choice);
        }
    }


    private static void personalInfoOfStudent() {
        System.out.println(loggedInStudent.getInfo());
        System.out.println("Press enter to continue...");
        studentPanel();
    }

    private static void viewStudentMarks() {
        markService.updateJournal(loggedInStudent);
        Map<Subject,ArrayList<Mark>> journal = loggedInStudent.getJournal();
        StringBuilder info = new StringBuilder();
        journal.forEach((k,v) -> {
            info.append(k.getName()).append(":");
            v.forEach((mark -> {
                info.append(mark);
            }));
            info.append(System.lineSeparator());
        });
        System.out.println(info.toString());
        System.out.println("");
        System.out.println("Press enter to continue...");
        scanner.nextLine();
        studentPanel();
    }

    private static void viewStudentAverageMarks() {
        Map<Subject,ArrayList<Mark>> journal = loggedInStudent.getJournal();
        StringBuilder info = new StringBuilder();
        journal.forEach((k,v) -> {
            info.append(k.getName()).append(":");
            double sum = v.stream().mapToDouble(Mark::getValue).sum();
            info.append(sum / v.size());
            info.append(System.lineSeparator());
        });
        System.out.println(info.toString());
        System.out.println("");
        System.out.println("Press enter to continue...");
        scanner.nextLine();
        studentPanel();
    }



    private static void removeStudent() {
        AtomicInteger counter = new AtomicInteger();
        studentService.getStudents().forEach(student -> {
            counter.getAndIncrement();
            System.out.println(counter + ". " + student.getFirstName() + " " + student.getLastName());
        });
        int choice = Integer.parseInt(scanner.nextLine()) - 1;

        try {
            Student student = studentService.getStudents().get(choice);
            studentService.removeStudent(student);
        } catch (SchoolException e) {
            e.printStackTrace();
            System.out.println("Can not write deactivated student" + e.getMessage());
        }

    }
    private static void removeTeacher() {
        AtomicInteger counter = new AtomicInteger();
        teacherService.getTeachers().forEach(teacher -> {
            counter.getAndIncrement();
            System.out.println(counter + ". " + teacher.getFirstName() + " " + teacher.getLastName());
        });
        int choice = Integer.parseInt(scanner.nextLine()) - 1;

        try {
            Teacher teacher = teacherService.getTeachers().get(choice);
            teacherService.removeTeacher(teacher);
        } catch (SchoolException e) {
            e.printStackTrace();
            System.out.println("Can not write deactivated Teacher" + e.getMessage());
        }

    }
    private static void removeClassroom() {
        AtomicInteger counter = new AtomicInteger();
        classroomsService.getClassrooms().forEach(classroom -> {
            counter.getAndIncrement();
            System.out.println(counter + ". " + classroom.getName() + " " + classroom.getId());
        });

        int choice = Integer.parseInt(scanner.nextLine()) - 1;

        try {
            Classroom classroom = classroomsService.getClassrooms().get(choice);
            classroomsService.removeClassroom(classroom);
        } catch (SchoolException e) {
            e.printStackTrace();
            System.out.println("Can not write deactivated Classroom" + e.getMessage());
        }
    }
    private static void removeSubject() {
        AtomicInteger counter = new AtomicInteger();
        subjectService.getSubjects().forEach(subject -> {
            counter.getAndIncrement();
            System.out.println(counter + ". " + subject.getName());
        });

        int choice = Integer.parseInt(scanner.nextLine()) - 1;

        try {
            Subject subject = subjectService.getSubjects().get(choice);
            subjectService.removeSubject(subject);
        } catch (SchoolException e) {
            e.printStackTrace();
            System.out.println("Can not write deactivated Subject" + e.getMessage());
        }
    }

    static void launch(){
        enterSystemPanel();
    }
}
