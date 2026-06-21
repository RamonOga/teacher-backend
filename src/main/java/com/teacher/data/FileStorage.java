package com.teacher.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

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

    private <T> List<T> loadList(String filename, List<T> defaults) {
        File file = dataDir.resolve(filename).toFile();
        if (file.exists()) {
            try {
                return mapper.readValue(file, new TypeReference<List<T>>() {});
            } catch (IOException e) {
                System.err.println("Failed to load " + filename + ", using defaults: " + e.getMessage());
            }
        }
        return defaults;
    }

    private void saveList(String filename, Object list) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(dataDir.resolve(filename).toFile(), list);
        } catch (IOException e) {
            System.err.println("Failed to save " + filename + ": " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadAll() {
        List<ScheduleItem> defSchedule = defaultSchedule();
        List<Material> defMaterials = defaultMaterials();
        List<Homework> defHomeworks = defaultHomeworks();
        List<BlogPost> defBlog = defaultBlog();

        schedule.addAll(loadList("schedule.json", defSchedule));
        materials.addAll(loadList("materials.json", defMaterials));
        homeworks.addAll(loadList("homework.json", defHomeworks));
        blogPosts.addAll(loadList("blog.json", defBlog));

        // Recalculate IDs
        materialId.set(materials.stream().mapToLong(Material::getId).max().orElse(0) + 1);
        homeworkId.set(homeworks.stream().mapToLong(Homework::getId).max().orElse(0) + 1);
        blogId.set(blogPosts.stream().mapToLong(BlogPost::getId).max().orElse(0) + 1);
    }

    // === Schedule ===
    public List<ScheduleItem> getSchedule() { return schedule; }

    public void saveSchedule(List<ScheduleItem> items) {
        schedule.clear();
        schedule.addAll(items);
        saveList("schedule.json", items);
    }

    public void updateScheduleItem(int index, ScheduleItem item) {
        if (index >= 0 && index < schedule.size()) {
            schedule.set(index, item);
            saveList("schedule.json", new ArrayList<>(schedule));
        }
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

    // === Default data (seeded on first run) ===
    private List<ScheduleItem> defaultSchedule() {
        return List.of(
            new ScheduleItem("Понедельник", "8:30–9:15", "7А", ""),
            new ScheduleItem("Понедельник", "9:25–10:10", "8Б", ""),
            new ScheduleItem("Понедельник", "10:25–11:10", "9В", ""),
            new ScheduleItem("Понедельник", "11:30–12:15", "10А", ""),
            new ScheduleItem("Вторник", "8:30–9:15", "7Б", ""),
            new ScheduleItem("Вторник", "9:25–10:10", "8А", ""),
            new ScheduleItem("Вторник", "14:00–15:30", "", "Факультатив: Python"),
            new ScheduleItem("Среда", "9:25–10:10", "9А", ""),
            new ScheduleItem("Среда", "10:25–11:10", "10Б", ""),
            new ScheduleItem("Среда", "11:30–12:15", "11А", ""),
            new ScheduleItem("Четверг", "8:30–9:15", "7В", ""),
            new ScheduleItem("Четверг", "9:25–10:10", "8Б", ""),
            new ScheduleItem("Четверг", "14:00–15:30", "", "Консультация"),
            new ScheduleItem("Пятница", "8:30–9:15", "9В", ""),
            new ScheduleItem("Пятница", "9:25–10:10", "10А", ""),
            new ScheduleItem("Пятница", "10:25–11:10", "11Б", "")
        );
    }

    private List<Material> defaultMaterials() {
        return List.of(
            new Material(1L, "Основы алгоритмизации", "Презентация · 9 класс", "/files/algo.pdf", "Презентации"),
            new Material(2L, "Python: введение", "Конспект · 10 класс", "/files/python-intro.pdf", "Конспекты"),
            new Material(3L, "Системы счисления", "Презентация · 8 класс", "/files/num-sys.pdf", "Презентации"),
            new Material(4L, "Базы данных: SQL", "Материалы · 11 класс", "/files/sql.pdf", "Материалы")
        );
    }

    private List<Homework> defaultHomeworks() {
        return List.of(
            new Homework(1L, "7 классы", "до 20.06", "Задание 15 в рабочей тетради."),
            new Homework(2L, "8 классы", "до 21.06", "Калькулятор на Python."),
            new Homework(3L, "9 классы", "до 22.06", "Подготовка к контрольной."),
            new Homework(4L, "10 классы", "до 23.06", "SQL запросы."),
            new Homework(5L, "11 классы", "до 24.06", "ЕГЭ вариант 23.")
        );
    }

    private List<BlogPost> defaultBlog() {
        return List.of(
            new BlogPost(1L, "Контрольная по системам счисления", "12 июня 2026", "На следующей неделе у 9-х классов контрольная.", "Полный текст..."),
            new BlogPost(2L, "Олимпиада по программированию", "5 июня 2026", "Запись на школьный этап открыта.", "Полный текст..."),
            new BlogPost(3L, "Почему Python — это не страшно", "28 мая 2026", "Рассказываю, с чего начать.", "Полный текст...")
        );
    }
}
