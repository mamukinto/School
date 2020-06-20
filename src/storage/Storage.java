package storage;

import model.classrom.Classroom;
import model.subject.Subject;
import model.user.student.Student;
import model.user.teacher.Teacher;

import java.util.List;

public abstract class Storage {

    public static List<Classroom> classrooms;

    public static List<Subject> subjects;

    public static List<Student> students;

    public static List<Teacher> teachers;
}
