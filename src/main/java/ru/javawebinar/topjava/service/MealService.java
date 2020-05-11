package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalTime;
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

    public MealTo create(Meal meal, int userId) {
        if (meal != null && meal.getUserId() == userId) {
            repository.save(meal);
            return convert(meal);
        } else throw new NotFoundException("Not found entity with id=" + meal.getId());
    }

    public boolean delete(int id, int userId) {
        // два обращения к репозиторию пока не придумал как убрать
        if (repository.getById(id).getUserId() == userId) {
            return repository.delete(id);
        } else throw new NotFoundException("Not found entity with id=" + id);
    }

    public MealTo get(int id, int userId) {
        Meal meal = repository.getById(id);
        if (meal != null && meal.getUserId() == userId) {
            return convert(meal);
        } else throw new NotFoundException("Not found entity with id=" + id);
    }

    public List<MealTo> getAll() {
        return MealsUtil.filteredByStreams(repository.getAll(), LocalTime.MIN, LocalTime.MAX, SecurityUtil.authUserCaloriesPerDay());
    }

    public MealTo update(Meal meal, int userId) {
        if (meal != null && meal.getUserId() == userId) {
            return convert(repository.save(meal));
        } else throw new NotFoundException("Not found entity with id=" + meal.getId());
    }

    private MealTo convert(Meal meal) {
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(repository.getAll(), LocalTime.MIN, LocalTime.MAX, SecurityUtil.authUserCaloriesPerDay());
        return mealsTo.stream()
                .filter(m -> m.getId() == meal.getId())
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Not found entity with id=" + meal.getId()));
    }

}