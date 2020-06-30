package ru.test.calendarnotes.data;

import java.security.Timestamp;

public class TaskModel {
    private int id;
    private Timestamp date_start;
    private Timestamp date_finish;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate_start() {
        return date_start;
    }

    public void setDate_start(Timestamp date_start) {
        this.date_start = date_start;
    }

    public Timestamp getDate_finish() {
        return date_finish;
    }

    public void setDate_finish(Timestamp date_finish) {
        this.date_finish = date_finish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
