package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryMealRepository implements MealRepository {
    private final ConcurrentMap<Long, Meal> mealStorage;
    private static final AtomicLong mealId = new AtomicLong();

    public InMemoryMealRepository() {
        this.mealStorage = new ConcurrentHashMap<>();
    }

    @Override
    public void save(Meal meal) {
        long mealId = meal.getId();
        if (mealId == 0 ) {
            mealId = generateUUID();
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

    public long generateUUID() {
        return mealId.incrementAndGet();
    }
}
