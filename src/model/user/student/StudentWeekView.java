package model.user.student;


public class StudentWeekView {
    private String name;

    private String mondayMark;

    private String tuesdayMark;

    private String wednesdayMark;

    private String thursdayMark;

    private String fridayMark;

    private String personalId;

    public StudentWeekView() {

    }

    public StudentWeekView(String name, String mondayMark, String tuesdayMark, String wednesdayMark, String thursdayMark, String fridayMark, String personalId) {
        this.name = name;
        this.mondayMark = mondayMark;
        this.tuesdayMark = tuesdayMark;
        this.wednesdayMark = wednesdayMark;
        this.thursdayMark = thursdayMark;
        this.fridayMark = fridayMark;
        this.personalId = personalId;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
