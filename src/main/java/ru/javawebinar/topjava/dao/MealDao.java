package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    void add(Meal meal);
    void delete(long mealId);
    void update(Meal meal);
    List<Meal> getAll();
    List<Meal> getAllSorted();
    Meal getById(long mealId);
}
