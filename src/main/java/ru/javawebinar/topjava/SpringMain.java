package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            User user = new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN);
            adminUserController.create(user);
            adminUserController.update(user, user.getId());
            adminUserController.get(user.getId());
            adminUserController.getByMail("email@mail.ru");
            adminUserController.getByMail("1email@mail.ru1");
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            List<MealTo> meals = mealRestController.getAll();
            meals.forEach(System.out::println);
        }
    }
}
