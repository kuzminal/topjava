package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class MealMemoryCrud implements MealDao {
    private ConcurrentMap<Long, Meal> mealStorage;

    public MealMemoryCrud(List<Meal> meals) {
        this.mealStorage = meals.stream()
                .collect(Collectors.toConcurrentMap(Meal::getId, meal -> meal));
    }

    @Override
    public void save(Meal meal) {
        long mealId = meal.getId();
        if (mealId == 0 ) {
            mealId = MealsUtil.generateUUID();
            meal.setId(mealId);
        }
        mealStorage.put(meal.getId(), meal);
    }

    @Override
    public void delete(long mealId) {
        mealStorage.remove(mealId);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealStorage.values());
    }

    @Override
    public Meal getById(long mealId) {
        return mealStorage.get(mealId);
    }
}
