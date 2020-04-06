package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.stream.Collectors;

public class MealMemoryCrud implements MealDao {
    private Map<Long, Meal> mealStorage = new HashMap<>();
    private static final MealMemoryCrud INSTANCE = new MealMemoryCrud();

    public MealMemoryCrud() {
        this.mealStorage = MealsUtil.getMeals().stream()
        .collect(Collectors.toMap(Meal::getId, meal -> meal));
    }

    public static MealMemoryCrud get() {
        return INSTANCE;
    }

    @Override
    public void add(Meal meal) {
        mealStorage.put(meal.getId(), meal);
    }

    @Override
    public void delete(long mealId) {
        mealStorage.remove(mealId);
    }

    @Override
    public void update(Meal meal) {
        mealStorage.remove(meal);
        mealStorage.put(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealStorage.values());
    }

    @Override
    public Meal getById(long mealId) {
        return mealStorage.get(mealId);
    }

    @Override
    public List<Meal> getAllSorted() {
        List<Meal> meals = new ArrayList<>(mealStorage.values());
        meals.sort(Comparator.comparing(Meal::getDateTime));
        return meals;
    }
}
