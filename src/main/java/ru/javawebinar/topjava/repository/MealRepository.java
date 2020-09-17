package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {
    Meal save(Meal meal, int userId);
    boolean delete(int mealId, int userId);
    List<Meal> getAll(int userId);
    List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId);
    Meal getById(int mealId, int userId);
    default Meal getWithUser(int mealId, int userId) {
        throw new UnsupportedOperationException("Method is not supported");
    }
}
