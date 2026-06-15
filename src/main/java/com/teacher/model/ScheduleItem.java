package com.teacher.model;

public class ScheduleItem {
    private String day;
    private String time;
    private String group;
    private String note;

    public ScheduleItem() {}

    public ScheduleItem(String day, String time, String group, String note) {
        this.day = day;
        this.time = time;
        this.group = group;
        this.note = note;
    }

    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
