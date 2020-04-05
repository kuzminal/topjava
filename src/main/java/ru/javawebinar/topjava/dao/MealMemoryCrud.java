package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MealMemoryCrud implements MealDao {
    private List<MealTo> mealStorage = new ArrayList<>();
    private static final MealMemoryCrud INSTANCE = new MealMemoryCrud();

    public MealMemoryCrud() {
        this.mealStorage = MealsUtil.convertMealsToTO(MealsUtil.getMeals());
    }

    public static MealMemoryCrud get() {
        return INSTANCE;
    }

    @Override
    public void addMeal(MealTo meal) {
        mealStorage.add(meal);
        mealStorage = MealsUtil.recalculateExceed(mealStorage);
    }

    @Override
    public void deleteMeal(String mealId) {
        MealTo meal = getMealById(mealId);
        if (meal != null) {
            mealStorage.remove(meal);
            mealStorage = MealsUtil.recalculateExceed(mealStorage);
        }
    }

    @Override
    public void updateMeal(MealTo meal) {
        mealStorage.remove(meal);
        mealStorage.add(meal);
        mealStorage = MealsUtil.recalculateExceed(mealStorage);
    }

    @Override
    public List<MealTo> getAllMeals() {
        return mealStorage;
    }

    @Override
    public MealTo getMealById(String mealId) {
        return mealStorage.stream()
                .filter(m -> m.getId().equals(mealId))
                .findFirst().orElse(null);
    }

    @Override
    public List<MealTo> getAllSortedMeals() {
        return mealStorage.stream()
                .sorted(Comparator.comparing(MealTo::getDateTime))
                .collect(Collectors.toList());
    }
}
