package model.user.student;

public class SubjectWeekViewForStudent {

    private String subjectName;

    private String mondayMark;

    private String tuesdayMark;

    private String wednesdayMark;

    private String thursdayMark;

    private String fridayMark;


    public SubjectWeekViewForStudent() {
    }

    public SubjectWeekViewForStudent(String subjectName, String mondayMark, String tuesdayMark, String wednesdayMark, String thursdayMark, String fridayMark) {
        this.subjectName = subjectName;
        this.mondayMark = mondayMark;
        this.tuesdayMark = tuesdayMark;
        this.wednesdayMark = wednesdayMark;
        this.thursdayMark = thursdayMark;
        this.fridayMark = fridayMark;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getMondayMark() {
        return mondayMark;
    }

    public void setMondayMark(String mondayMark) {
        this.mondayMark = mondayMark;
    }

    public String getTuesdayMark() {
        return tuesdayMark;
    }

    public void setTuesdayMark(String tuesdayMark) {
        this.tuesdayMark = tuesdayMark;
    }

    public String getWednesdayMark() {
        return wednesdayMark;
    }

    public void setWednesdayMark(String wednesdayMark) {
        this.wednesdayMark = wednesdayMark;
    }

    public String getThursdayMark() {
        return thursdayMark;
    }

    public void setThursdayMark(String thursdayMark) {
        this.thursdayMark = thursdayMark;
    }

    public String getFridayMark() {
        return fridayMark;
    }

    public void setFridayMark(String fridayMark) {
        this.fridayMark = fridayMark;
    }
}
