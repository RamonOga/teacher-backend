package com.teacher.repository;

import com.teacher.data.FileStorage;
import com.teacher.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataStore {

    private final FileStorage storage;

    public DataStore(FileStorage storage) {
        this.storage = storage;
    }

    public List<ScheduleItem> getSchedule() { return storage.getSchedule(); }
    public List<Material> getMaterials() { return storage.getMaterials(); }
    public List<Homework> getHomeworks() { return storage.getHomeworks(); }
    public List<BlogPost> getBlogPosts() { return storage.getBlogPosts(); }
}
