package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

@Controller
public class MealRestController {
    private MealService service;

    @Autowired
    public void setService(MealService service) {
        this.service = service;
    }

    public List<Meal> getAll() {
        return service.getAll();
    }

    public boolean delete(int id, int userId) {
        return service.delete(id, userId);
    }

    public Meal update(Meal meal) {
        return service.update(meal);
    }

    public Meal get(int id, int userId) {
        return service.get(id, userId);
    }
}