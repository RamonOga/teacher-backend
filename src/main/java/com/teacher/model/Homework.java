package com.teacher.model;

public class Homework {
    private Long id;
    private String group;
    private String deadline;
    private String description;

    public Homework() {}

    public Homework(Long id, String group, String deadline, String description) {
        this.id = id;
        this.group = group;
        this.deadline = deadline;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }
    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
