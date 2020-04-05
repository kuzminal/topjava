package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealDao {
    void addMeal(MealTo meal);
    void deleteMeal(String mealId);
    void updateMeal(MealTo meal);
    List<MealTo> getAllMeals();
    List<MealTo> getAllSortedMeals();
    MealTo getMealById(String mealId);
}
