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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

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

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.getById(id, userId), id);
    }

    public List<MealTo> getAll(int userId) {
        return MealsUtil.filteredByStreams(repository.getAll(userId), LocalTime.MIN, LocalTime.MAX, SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getAllFiltered(int userId, Map<String, String> filterPrams) {
        LocalDate startDate = filterPrams.get("startDate") != "" ? LocalDate.parse(filterPrams.get("startDate")) : LocalDate.MIN;
        LocalDate endDate = filterPrams.get("dateEnd") != "" ? LocalDate.parse(filterPrams.get("dateEnd")) : LocalDate.MAX;
        LocalTime startTime = filterPrams.get("timeStart") != "" ? LocalTime.parse(filterPrams.get("timeStart")) : LocalTime.MIN;
        LocalTime endTime = filterPrams.get("timeEnd") != "" ? LocalTime.parse(filterPrams.get("timeEnd")) : LocalTime.MAX;
        return MealsUtil.filteredByStreams(repository.getFiltered(userId, startDate, endDate), startTime, endTime, SecurityUtil.authUserCaloriesPerDay());
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }
}