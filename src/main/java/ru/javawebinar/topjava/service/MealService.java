package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.web.SecurityUtil;
import java.util.List;

@Service
public class MealService {

    private MealRepository repository;

    @Autowired
    @Qualifier("inMemoryRepository")
    public void setRepository(MealRepository repository) {
        this.repository = repository;
    }

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        if (meal != null && meal.getUserId() == SecurityUtil.authUserId()) {
            repository.save(meal);
            return meal;
        } else return null;
    }

    public boolean delete(int id) {
        if (get(id).getUserId() == SecurityUtil.authUserId()) {
            return repository.delete(id);
        } else return false;
    }

    public Meal get(int id) {
        Meal meal = repository.getById(id);
        if (meal != null && meal.getUserId() == SecurityUtil.authUserId()) {
            return meal;
        } else return null;
    }

    public List<Meal> getAll() {
        return repository.getAll();
    }

    public Meal update(Meal meal) {
        if (meal != null && meal.getUserId() == SecurityUtil.authUserId()) {
            return repository.save(meal);
        } else return null;
    }

}