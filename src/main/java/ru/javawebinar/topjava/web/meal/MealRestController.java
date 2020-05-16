package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private MealService service;

    @Autowired
    public void setService(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return service.getAll(authUserId());
    }

    public void delete(int id) {
        log.info("delete");
        service.delete(id, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update");
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }

    public Meal save(Meal meal) {
        log.info("save");
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public Meal get(int id) {
        log.info("get");
        return service.get(id, authUserId());
    }

    public List<MealTo> filter(Map<String, String> filterPrams) {
        return service.getAllFiltered(authUserId(), filterPrams);
    }
}