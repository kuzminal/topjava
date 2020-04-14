package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    void save(Meal meal);
    void delete(long mealId);
    List<Meal> getAll();
    Meal getById(long mealId);
}
