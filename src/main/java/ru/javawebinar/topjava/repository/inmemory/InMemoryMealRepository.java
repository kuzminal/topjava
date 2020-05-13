package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository("inMemoryRepository")
public class InMemoryMealRepository implements MealRepository {
    private final ConcurrentMap<Integer, Meal> mealStorage;
    private static final AtomicInteger mealId = new AtomicInteger();

    public InMemoryMealRepository() {
        this.mealStorage = new ConcurrentHashMap<>();
        MealsUtil.getMeals().forEach(meal -> {
            save(meal, 1);
        });
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(generateUUID());
            return mealStorage.put(meal.getId(), meal);
        } else {
            Meal storedMeal = mealStorage.get(meal.getId());
            if (storedMeal == null) {
                return null;
            } else if (storedMeal.getUserId() == userId) {
                return mealStorage.put(meal.getId(), meal);
            } else return null;
        }
    }

    @Override
    public boolean delete(int mealId, int userId) {
        Meal storedMeal = mealStorage.get(mealId);
        if (storedMeal == null) {
            return false;
        } else if (storedMeal.getUserId() == userId) {
            Meal meal = mealStorage.remove(mealId);
            if (meal == storedMeal) {
                return true;
            } else return false;
        } else return false;
    }

    @Override
    public List<Meal> getAll() {
        return mealStorage.values().stream()
                .sorted((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Meal getById(int mealId, int userId) {
        Meal storedMeal = mealStorage.get(mealId);
        if (storedMeal == null) {
            return null;
        } else if (storedMeal.getUserId() == userId) {
            return mealStorage.get(mealId);
        } else return null;
    }

    public int generateUUID() {
        return mealId.incrementAndGet();
    }
}
