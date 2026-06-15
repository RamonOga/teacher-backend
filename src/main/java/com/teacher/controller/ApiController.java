package com.teacher.controller;

import com.teacher.model.*;
import com.teacher.repository.DataStore;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final DataStore store;

    public ApiController(DataStore store) {
        this.store = store;
    }

    @GetMapping("/schedule")
    public List<ScheduleItem> getSchedule() {
        return store.getSchedule();
    }

    @GetMapping("/materials")
    public List<Material> getMaterials() {
        return store.getMaterials();
    }

    @GetMapping("/homework")
    public List<Homework> getHomework() {
        return store.getHomeworks();
    }

    @GetMapping("/blog")
    public List<BlogPost> getBlog() {
        return store.getBlogPosts();
    }
}
