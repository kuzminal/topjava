package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
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
        if (meal.getId() == 0 || meal.getId() == null) {
            meal.setId(generateUUID());
        }
        mealStorage.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int mealId) {
        mealStorage.remove(mealId);
        return true;
    }

    @Override
    public List<Meal> getAll() {
        return mealStorage.values().stream()
                .sorted((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Meal getById(int mealId) {
        return mealStorage.get(mealId);
    }

    public int generateUUID() {
        return mealId.incrementAndGet();
    }
}
