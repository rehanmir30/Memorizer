package com.example.memorizer.model;

public class SavedTasks {
    String description, title, time, color;

    public SavedTasks(String description, String title, String time, String color) {
        this.description = description;
        this.title = title;
        this.time = time;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
