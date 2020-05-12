package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

public interface MealRepository {
    Meal save(Meal meal, int userId);
    boolean delete(int mealId, int userId);
    List<Meal> getAll();
    Meal getById(int mealId, int userId);
}
