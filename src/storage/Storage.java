package storage;

import model.classrom.Classroom;
import model.user.student.Student;
import model.subject.Subject;
import model.user.teacher.Teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Storage {

    public static List<Classroom> standartClassrooms = new ArrayList<>(Arrays.asList(
            new Classroom("1",1),
            new Classroom("2",2),
            new Classroom("3",3),
            new Classroom("4",4),
            new Classroom("5",5),
            new Classroom("6",6),
            new Classroom("7",7),
            new Classroom("8",8),
            new Classroom("9",9),
            new Classroom("10",10),
            new Classroom("11",11),
            new Classroom("12",12)
    ));

    public static List<Subject> standartSubjects = new ArrayList<>(Arrays.asList(
            new Subject("Geography",20),
            new Subject("Physics",30),
            new Subject("Biology",10),
            new Subject("Mathematics",50)
    ));

    public static List<Classroom> classrooms;

    public static List<Subject> subjects;

    public static List<Student> students;

    public static List<Teacher> teachers;
}
