package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

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
        return repository.save(meal);
    }

    public boolean delete(int id, int userId) {
        return repository.delete(id, userId);
    }

    public Meal get(int id, int userId) {
        return repository.getById(id, userId);
    }

    public List<Meal> getAll() {
        return repository.getAll();
    }

    public Meal update(Meal meal) {
        return repository.save(meal);
    }

}