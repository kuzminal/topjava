package ru.javawebinar.topjava;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            User user = new User(null, "userName", "email@mail.ru", "password", Role.ADMIN);
            adminUserController.create(user);
            adminUserController.update(user, user.getId());
            adminUserController.get(user.getId());
            adminUserController.getByMail("email@mail.ru");
            //adminUserController.getByMail("1email@mail.ru1");
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            List<MealTo> meals = mealRestController.getAll();
            Meal meal1 = new Meal(null, LocalDateTime.of(2020, Month.MAY, 12, 10, 0), "Завтрак", 180);
            Meal meal2 = new Meal(null, LocalDateTime.of(2020, Month.MAY, 12, 10, 0), "Завтрак", 180);
            mealRestController.getAll();
            mealRestController.delete(2);
            //mealRestController.delete(8);
            mealRestController.save(meal1);
            mealRestController.save(meal2);
            mealRestController.update(meal1, meal1.getId());
            mealRestController.update(meal2, meal2.getId());
            mealRestController.get(2);
            mealRestController.get(meal2.getId());
            meals.forEach(System.out::println);
        }
    }
}
