package com.teacher.controller;

import com.teacher.auth.AuthFilter;
import com.teacher.data.FileStorage;
import com.teacher.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final FileStorage storage;

    public AdminController(FileStorage storage) {
        this.storage = storage;
    }

    // === Auth ===
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String token = AuthFilter.login(body.get("login"), body.get("password"));
        if (token == null) {
            return Map.of("error", "Invalid credentials");
        }
        return Map.of("token", token);
    }

    @PostMapping("/logout")
    public Map<String, String> logout(@RequestHeader("X-Admin-Token") String token) {
        AuthFilter.logout(token);
        return Map.of("status", "ok");
    }

    // === Schedule ===
    @GetMapping("/schedule")
    public List<ScheduleItem> getSchedule() {
        return storage.getSchedule();
    }

    @PutMapping("/schedule")
    public Map<String, String> updateSchedule(@RequestBody List<ScheduleItem> items) {
        storage.saveSchedule(items);
        return Map.of("status", "ok");
    }

    // === Materials ===
    @GetMapping("/materials")
    public List<Material> getMaterials() {
        return storage.getMaterials();
    }

    @PostMapping("/materials")
    public Material addMaterial(@RequestBody Material m) {
        return storage.addMaterial(m);
    }

    @PutMapping("/materials/{id}")
    public Map<String, String> updateMaterial(@PathVariable Long id, @RequestBody Material m) {
        storage.updateMaterial(id, m);
        return Map.of("status", "ok");
    }

    @DeleteMapping("/materials/{id}")
    public Map<String, String> deleteMaterial(@PathVariable Long id) {
        storage.deleteMaterial(id);
        return Map.of("status", "ok");
    }

    // === Homework ===
    @GetMapping("/homework")
    public List<Homework> getHomework() {
        return storage.getHomeworks();
    }

    @PostMapping("/homework")
    public Homework addHomework(@RequestBody Homework h) {
        return storage.addHomework(h);
    }

    @PutMapping("/homework/{id}")
    public Map<String, String> updateHomework(@PathVariable Long id, @RequestBody Homework h) {
        storage.updateHomework(id, h);
        return Map.of("status", "ok");
    }

    @DeleteMapping("/homework/{id}")
    public Map<String, String> deleteHomework(@PathVariable Long id) {
        storage.deleteHomework(id);
        return Map.of("status", "ok");
    }

    // === Blog ===
    @GetMapping("/blog")
    public List<BlogPost> getBlog() {
        return storage.getBlogPosts();
    }

    @PostMapping("/blog")
    public BlogPost addBlogPost(@RequestBody BlogPost p) {
        return storage.addBlogPost(p);
    }

    @PutMapping("/blog/{id}")
    public Map<String, String> updateBlogPost(@PathVariable Long id, @RequestBody BlogPost p) {
        storage.updateBlogPost(id, p);
        return Map.of("status", "ok");
    }

    @DeleteMapping("/blog/{id}")
    public Map<String, String> deleteBlogPost(@PathVariable Long id) {
        storage.deleteBlogPost(id);
        return Map.of("status", "ok");
    }
}
