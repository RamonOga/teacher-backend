package com.teacher.repository;

import com.teacher.model.*;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DataStore {

    private final List<ScheduleItem> schedule = new CopyOnWriteArrayList<>();
    private final List<Material> materials = new CopyOnWriteArrayList<>();
    private final List<Homework> homeworks = new CopyOnWriteArrayList<>();
    private final List<BlogPost> blogPosts = new CopyOnWriteArrayList<>();

    private final AtomicLong materialId = new AtomicLong(1);
    private final AtomicLong homeworkId = new AtomicLong(1);
    private final AtomicLong blogId = new AtomicLong(1);

    @PostConstruct
    public void init() {
        // Schedule
        schedule.add(new ScheduleItem("Понедельник", "8:30–9:15", "7А", ""));
        schedule.add(new ScheduleItem("Понедельник", "9:25–10:10", "8Б", ""));
        schedule.add(new ScheduleItem("Понедельник", "10:25–11:10", "9В", ""));
        schedule.add(new ScheduleItem("Понедельник", "11:30–12:15", "10А", ""));
        schedule.add(new ScheduleItem("Вторник", "8:30–9:15", "7Б", ""));
        schedule.add(new ScheduleItem("Вторник", "9:25–10:10", "8А", ""));
        schedule.add(new ScheduleItem("Вторник", "14:00–15:30", "", "Факультатив: Python"));
        schedule.add(new ScheduleItem("Среда", "9:25–10:10", "9А", ""));
        schedule.add(new ScheduleItem("Среда", "10:25–11:10", "10Б", ""));
        schedule.add(new ScheduleItem("Среда", "11:30–12:15", "11А", ""));
        schedule.add(new ScheduleItem("Четверг", "8:30–9:15", "7В", ""));
        schedule.add(new ScheduleItem("Четверг", "9:25–10:10", "8Б", ""));
        schedule.add(new ScheduleItem("Четверг", "14:00–15:30", "", "Консультация"));
        schedule.add(new ScheduleItem("Пятница", "8:30–9:15", "9В", ""));
        schedule.add(new ScheduleItem("Пятница", "9:25–10:10", "10А", ""));
        schedule.add(new ScheduleItem("Пятница", "10:25–11:10", "11Б", ""));

        // Materials
        materials.add(new Material(materialId.getAndIncrement(), "Основы алгоритмизации", "Презентация · 9 класс", "/files/algo.pdf", "Презентации"));
        materials.add(new Material(materialId.getAndIncrement(), "Python: введение", "Конспект · 10 класс", "/files/python-intro.pdf", "Конспекты"));
        materials.add(new Material(materialId.getAndIncrement(), "Системы счисления", "Презентация · 8 класс", "/files/num-sys.pdf", "Презентации"));
        materials.add(new Material(materialId.getAndIncrement(), "Базы данных: SQL", "Материалы · 11 класс", "/files/sql.pdf", "Материалы"));

        // Homework
        homeworks.add(new Homework(homeworkId.getAndIncrement(), "7 классы", "до 20.06", "Задание 15 в рабочей тетради. Доделать задачу про циклы."));
        homeworks.add(new Homework(homeworkId.getAndIncrement(), "8 классы", "до 21.06", "Написать программу на Python: калькулятор с четырьмя действиями."));
        homeworks.add(new Homework(homeworkId.getAndIncrement(), "9 классы", "до 22.06", "Подготовиться к контрольной: системы счисления, таблицы истинности."));
        homeworks.add(new Homework(homeworkId.getAndIncrement(), "10 классы", "до 23.06", "Сделать запросы к БД — файл прикрепил в беседе."));
        homeworks.add(new Homework(homeworkId.getAndIncrement(), "11 классы", "до 24.06", "Готовиться к ЕГЭ: вариант 23 из сборника."));

        // Blog
        blogPosts.add(new BlogPost(blogId.getAndIncrement(),
            "Контрольная по системам счисления", "12 июня 2026",
            "На следующей неделе у 9-х классов контрольная. Повторите перевод чисел, двоичную арифметику и таблицы истинности.",
            "Полный текст поста про контрольную..."));
        blogPosts.add(new BlogPost(blogId.getAndIncrement(),
            "Олимпиада по программированию", "5 июня 2026",
            "Запись на школьный этап открыта. Желающие — подойти в кабинет 24 до 15 июня.",
            "Полный текст поста про олимпиаду..."));
        blogPosts.add(new BlogPost(blogId.getAndIncrement(),
            "Почему Python — это не страшно", "28 мая 2026",
            "Рассказываю, с чего начать, если кажется, что программирование — это сложно.",
            "Полный текст поста про Python..."));
    }

    public List<ScheduleItem> getSchedule() { return schedule; }
    public List<Material> getMaterials() { return materials; }
    public List<Homework> getHomeworks() { return homeworks; }
    public List<BlogPost> getBlogPosts() { return blogPosts; }
}
