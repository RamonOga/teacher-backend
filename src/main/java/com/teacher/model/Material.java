package com.teacher.model;

public class Material {
    private Long id;
    private String title;
    private String description;
    private String fileUrl;
    private String category;

    public Material() {}

    public Material(Long id, String title, String description, String fileUrl, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.fileUrl = fileUrl;
        this.category = category;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
