package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.TestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal newMeal = getNewMeal();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMealMatch(created, newMeal);
        assertMealMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        service.get(MEAL_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, USER_ID);
        assertMealMatch(meal, MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatchMealList(all, getMeals());
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> meal = service.getBetweenHalfOpen(LocalDate.of(2020, 1,30), LocalDate.of(2020, 1,30), USER_ID);
        assertMatchMealList(meal, getMealsHalfOpen());
    }

    @Test
    public void update() {
        Meal updated = getUpdatedMeal();
        service.update(updated, USER_ID);
        assertMealMatch(service.get(MEAL_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal updated = getUpdatedMeal();
        service.update(updated, ADMIN_ID);
    }
}