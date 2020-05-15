package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class Meal extends AbstractBaseEntity {
    private LocalDateTime dateTime;
    private int userId;

    private String description;

    private int calories;

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        //this.userId = userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
