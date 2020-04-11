package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    void save(Meal meal);
    void delete(long mealId);
    List<Meal> getAll();
    Meal getById(long mealId);
}
