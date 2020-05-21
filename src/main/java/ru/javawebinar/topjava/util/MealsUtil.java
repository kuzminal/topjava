package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    private static final List<Meal> meals;
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    static {
        meals = Arrays.asList(
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
    }

    public static List<Meal> getMeals() {
        return meals;
    }

    public static int getCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

    public static void main(String[] args) {
        List<MealTo> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<MealTo> filteredByCycles(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumByDate = new HashMap<>();
        meals.forEach(meal ->
                sumByDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum)
        );
        List<MealTo> exceeded = new ArrayList<>();
        meals.forEach(meal ->
                {
                    if (Util.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                        boolean exceed = sumByDate.get(meal.getDateTime().toLocalDate()) > caloriesPerDay;
                        exceeded.add(new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed, meal.getId()));
                    }
                }
        );
        return exceeded;
    }

    public static List<MealTo> filteredByStreams(Collection<Meal> meals, Predicate<Meal> filter, int caloriesPerDay) {
        Map<LocalDate, Integer> sumByDate = meals.stream()
                .collect(Collectors.groupingBy(meal ->
                        meal.getDateTime().toLocalDate(), Collectors.summingInt(Meal::getCalories)));
        return meals.stream()
                .filter(filter)
                .map(exceededMeal ->
                        new MealTo(exceededMeal.getDateTime(), exceededMeal.getDescription(), exceededMeal.getCalories(),
                                sumByDate.get(exceededMeal.getDateTime().toLocalDate()) > caloriesPerDay, exceededMeal.getId()))
                .collect(Collectors.toList());
    }

    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return filteredByStreams(meals, meal -> Util.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime), caloriesPerDay);
    }
}
