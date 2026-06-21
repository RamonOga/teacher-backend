package com.teacher.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teacher.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class FileStorage {

    private final Path dataDir = Path.of("/data");
    private final ObjectMapper mapper = new ObjectMapper();

    private final List<ScheduleItem> schedule = new CopyOnWriteArrayList<>();
    private final List<Material> materials = new CopyOnWriteArrayList<>();
    private final List<Homework> homeworks = new CopyOnWriteArrayList<>();
    private final List<BlogPost> blogPosts = new CopyOnWriteArrayList<>();

    private final AtomicLong materialId = new AtomicLong(1);
    private final AtomicLong homeworkId = new AtomicLong(1);
    private final AtomicLong blogId = new AtomicLong(1);

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(dataDir);
            loadAll();
        } catch (IOException e) {
            throw new RuntimeException("Failed to init data dir", e);
        }
    }

    private <T> List<T> loadList(String filename, Class<T> elementType) {
        File file = dataDir.resolve(filename).toFile();
        if (file.exists()) {
            try {
                return mapper.readValue(file, new TypeReference<List<T>>() {});
            } catch (IOException e) {
                System.err.println("Failed to load " + filename + ": " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }

    private void saveList(String filename, Object list) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(dataDir.resolve(filename).toFile(), list);
        } catch (IOException e) {
            System.err.println("Failed to save " + filename + ": " + e.getMessage());
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void loadAll() {
        schedule.addAll((List)loadList("schedule.json", ScheduleItem.class));
        materials.addAll((List)loadList("materials.json", Material.class));
        homeworks.addAll((List)loadList("homework.json", Homework.class));
        blogPosts.addAll((List)loadList("blog.json", BlogPost.class));

        materialId.set(materials.stream().mapToLong(Material::getId).max().orElse(0) + 1);
        homeworkId.set(homeworks.stream().mapToLong(Homework::getId).max().orElse(0) + 1);
        blogId.set(blogPosts.stream().mapToLong(BlogPost::getId).max().orElse(0) + 1);
    }

    // === Schedule ===
    public List<ScheduleItem> getSchedule() { return schedule; }

    public void saveSchedule(List<ScheduleItem> items) {
        schedule.clear();
        schedule.addAll(items);
        saveList("schedule.json", new ArrayList<>(schedule));
    }

    // === Materials ===
    public List<Material> getMaterials() { return materials; }

    public Material addMaterial(Material m) {
        m.setId(materialId.getAndIncrement());
        materials.add(m);
        saveList("materials.json", new ArrayList<>(materials));
        return m;
    }

    public void updateMaterial(Long id, Material m) {
        for (int i = 0; i < materials.size(); i++) {
            if (materials.get(i).getId().equals(id)) {
                m.setId(id);
                materials.set(i, m);
                saveList("materials.json", new ArrayList<>(materials));
                return;
            }
        }
    }

    public void deleteMaterial(Long id) {
        materials.removeIf(m -> m.getId().equals(id));
        saveList("materials.json", new ArrayList<>(materials));
    }

    // === Homework ===
    public List<Homework> getHomeworks() { return homeworks; }

    public Homework addHomework(Homework h) {
        h.setId(homeworkId.getAndIncrement());
        homeworks.add(h);
        saveList("homework.json", new ArrayList<>(homeworks));
        return h;
    }

    public void updateHomework(Long id, Homework h) {
        for (int i = 0; i < homeworks.size(); i++) {
            if (homeworks.get(i).getId().equals(id)) {
                h.setId(id);
                homeworks.set(i, h);
                saveList("homework.json", new ArrayList<>(homeworks));
                return;
            }
        }
    }

    public void deleteHomework(Long id) {
        homeworks.removeIf(h -> h.getId().equals(id));
        saveList("homework.json", new ArrayList<>(homeworks));
    }

    // === Blog ===
    public List<BlogPost> getBlogPosts() { return blogPosts; }

    public BlogPost addBlogPost(BlogPost p) {
        p.setId(blogId.getAndIncrement());
        blogPosts.add(p);
        saveList("blog.json", new ArrayList<>(blogPosts));
        return p;
    }

    public void updateBlogPost(Long id, BlogPost p) {
        for (int i = 0; i < blogPosts.size(); i++) {
            if (blogPosts.get(i).getId().equals(id)) {
                p.setId(id);
                blogPosts.set(i, p);
                saveList("blog.json", new ArrayList<>(blogPosts));
                return;
            }
        }
    }

    public void deleteBlogPost(Long id) {
        blogPosts.removeIf(b -> b.getId().equals(id));
        saveList("blog.json", new ArrayList<>(blogPosts));
    }
}
