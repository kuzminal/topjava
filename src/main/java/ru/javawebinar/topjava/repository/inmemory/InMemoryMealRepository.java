package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalTime;
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
        MealsUtil.getMeals().forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.getUserId() == SecurityUtil.authUserId()) {
            if (meal.getId() == 0 || meal.getId() == null) {
                meal.setId(generateUUID());
            }
            mealStorage.put(meal.getId(), meal);
            return meal;
        } else return null;
    }

    @Override
    public boolean delete(int mealId, int userId) {
       Meal meal = getById(mealId, userId);
       if (meal != null) {
            mealStorage.remove(mealId);
            return true;
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
        Meal meal = mealStorage.get(mealId);
        if (meal != null && meal.getUserId() == SecurityUtil.authUserId()) {
            return mealStorage.get(mealId);
        } else return null;
    }

    public int generateUUID() {
        return mealId.incrementAndGet();
    }
}
