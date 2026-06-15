package com.teacher.model;

public class BlogPost {
    private Long id;
    private String title;
    private String date;
    private String excerpt;
    private String content;

    public BlogPost() {}

    public BlogPost(Long id, String title, String date, String excerpt, String content) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.excerpt = excerpt;
        this.content = content;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getExcerpt() { return excerpt; }
    public void setExcerpt(String excerpt) { this.excerpt = excerpt; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
