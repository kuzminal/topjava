package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class MealsUtil {
    private static final List<Meal> meals;
    private static final int caloriesPerDay = 2000;

    static {
        meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
    }

    public static List<Meal> getMeals() {
        return meals;
    }

    public static int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public static void main(String[] args) {
/*        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );*/

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
                    if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                        boolean exceed = sumByDate.get(meal.getDateTime().toLocalDate()) > caloriesPerDay;
                        exceeded.add(new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed));
                    }
                }
        );
        return exceeded;
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumByDate = meals.stream()
                .collect(Collectors.groupingBy(meal ->
                        meal.getDateTime().toLocalDate(), Collectors.summingInt(Meal::getCalories)));
        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(exceededMeal ->
                        new MealTo(exceededMeal.getDateTime(), exceededMeal.getDescription(), exceededMeal.getCalories(),
                                sumByDate.get(exceededMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealTo> convertMealsToTO(List<Meal> meals) {
        List<MealTo> exceeded = new ArrayList<>();
        MealsUtil.getMeals().forEach(meal ->
                {
                    boolean exceed = sumByDate(meals).get(meal.getDateTime().toLocalDate()) > MealsUtil.getCaloriesPerDay();
                    exceeded.add(new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed));
                }
        );
        return exceeded;
    }

    private static Map<LocalDate, Integer> sumByDate(List<Meal> meals) {
        Map<LocalDate, Integer> sumByDate = new HashMap<>();
        MealsUtil.getMeals().forEach(meal ->
                sumByDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum)
        );
        return sumByDate;
    }

    public static List<MealTo> recalculateExceed(List<MealTo> meals) {
        Map<LocalDate, Integer> sumByDate = meals.stream()
                .collect(Collectors.groupingBy(meal ->
                        meal.getDateTime().toLocalDate(), Collectors.summingInt(MealTo::getCalories)));
        List<MealTo> recalculatedMeals = new ArrayList<>();
        meals.forEach(meal ->
                {
                    boolean exceed = sumByDate.get(meal.getDateTime().toLocalDate()) > MealsUtil.getCaloriesPerDay();
                    recalculatedMeals.add(new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed));
                }
        );
        return recalculatedMeals;
    }

    public static synchronized String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
