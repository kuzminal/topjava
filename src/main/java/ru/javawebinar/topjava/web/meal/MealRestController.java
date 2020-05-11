package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private MealService service;

    @Autowired
    public void setService(MealService service) {
        this.service = service;
    }

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public boolean delete(int id, int userId) {
        log.info("delete");
        return service.delete(id);
    }

    public Meal update(Meal meal) {
        log.info("update");
        return service.update(meal);
    }

    public Meal save(Meal meal) {
        log.info("save");
        return service.create(meal);
    }

    public Meal get(int id) {
        log.info("get");
        return service.get(id);
    }


}