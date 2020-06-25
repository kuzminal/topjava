package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class TestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MEAL_ID = START_SEQ + 2;

    public static final User USER = new User(USER_ID, "user", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);
    public static final Meal MEAL = new Meal(MEAL_ID, LocalDateTime.of(2020,1, 30, 10, 0,0), "Завтрак", 500);
    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(START_SEQ + 2, LocalDateTime.of(2020, 1,30, 10, 0, 0), "Завтрак", 500),
            new Meal(START_SEQ + 3, LocalDateTime.of(2020, 1,30, 13, 0, 0), "Обед", 1000),
            new Meal(START_SEQ + 4, LocalDateTime.of(2020, 1,30, 20, 0, 0), "Ужин", 500),
            new Meal(START_SEQ + 5, LocalDateTime.of(2020, 1,31, 0, 0, 0), "Еда на граничное значение", 100),
            new Meal(START_SEQ + 6, LocalDateTime.of(2020, 1,31, 10, 0, 0), "Завтрак", 1000),
            new Meal(START_SEQ + 7, LocalDateTime.of(2020, 1,31, 13, 0, 0), "Обед", 500),
            new Meal(START_SEQ + 8, LocalDateTime.of(2020, 1,31, 20, 0, 0), "Ужин", 410));

    public static User getNew() {
        return new User(null, "new", "new@gmail.com", "newpass", 1555, false, new Date(), Collections.singleton(Role.ROLE_USER));
    }

    public static Meal getNewMeal() {
        Meal meal = new Meal(null, LocalDateTime.of(2020,1, 30, 18, 0,0), "test Meal", 700);
        return meal;
    }

    public static List<Meal> getMeals() {
        MEALS.sort(Comparator.comparing(Meal::getDateTime).reversed());
        return MEALS;
    }

    public static List<Meal> getMealsHalfOpen() {
        return MEALS.stream()
                .filter(meal -> meal.getDateTime().compareTo(LocalDateTime.of(2020, 1, 30, 0, 0, 0)) >= 0)
                .filter(meal -> meal.getDateTime().compareTo(LocalDateTime.of(2020, 1, 31, 0, 0, 0)) < 0)
                .collect(Collectors.toList());
    }

    public static Meal getUpdatedMeal() {
        Meal updated = new Meal(MEAL);
        updated.setDescription("UpdatedName");
        updated.setCalories(330);
        return updated;
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("updated_name");
        updated.setCaloriesPerDay(330);
        return updated;
    }

    public static void assertUserMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "roles");
    }

    public static void assertMealMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertUserMatch(Iterable<User> actual, User... expected) {
        assertMatchUserList(actual, Arrays.asList(expected));
    }

    public static void assertMatchUserList(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }

    public static void assertMatchMealList(Iterable<Meal> actual, Iterable<Meal> expected, String... fields) {
        assertThat(actual).usingElementComparatorIgnoringFields(fields).isEqualTo(expected);
    }
}
