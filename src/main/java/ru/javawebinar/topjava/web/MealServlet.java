package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealMemoryCrud;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private MealDao storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = MealMemoryCrud.get();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String mealId = request.getParameter("mealId") != null ? request.getParameter("mealId") : "";
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        log.debug("Action = " + action + " mealId = " + mealId );
        switch (action) {
            case "delete" :
                storage.delete(Long.parseLong(mealId));
                response.sendRedirect("meals");
                return;
            case "edit" :
                request.setAttribute("meal", storage.getById(Long.parseLong(mealId)));
                request.getRequestDispatcher("/mealsEdit.jsp").forward(request, response);
                break;
            case "add":
                Meal meal = new Meal(LocalDateTime.now(), "",0, MealsUtil.generateUUID());
                storage.add(meal);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealsEdit.jsp").forward(request, response);
                break;
        }
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.getCaloriesPerDay());
        mealsTo.sort(Comparator.comparing(MealTo::getDateTime));
        request.setAttribute("meals", mealsTo);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String mealId = request.getParameter("mealId") != null ? request.getParameter("mealId") : "";
        Meal meal = storage.getById(Long.parseLong(mealId));
        saveMeal(request, response, meal);
        response.sendRedirect("meals");
    }

    private void saveMeal(HttpServletRequest request, HttpServletResponse response, Meal meal) {
        String description = request.getParameter("description") != null ? request.getParameter("description") : "";
        String calories = request.getParameter("calories") != null ? request.getParameter("calories") : "";
        String dateTime = request.getParameter("dateTime") != null ? request.getParameter("dateTime") : "";
        if ( description.trim().length() != 0 && calories.trim().length() != 0 && dateTime.trim().length() != 0) {
            if (meal != null) {
                meal.setCalories(Integer.parseInt(calories));
                meal.setDescription(description);
                meal.setDateTime(LocalDateTime.parse(dateTime));
                storage.update(meal);
            } else {
                storage.add(new Meal(LocalDateTime.parse(dateTime), description, Integer.parseInt(calories), MealsUtil.generateUUID()));
            }
        }
    }
}
