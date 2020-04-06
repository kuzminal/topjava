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

import static org.slf4j.LoggerFactory.getLogger;

public class MealEditServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private MealDao storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = MealMemoryCrud.get();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Send redirect from meals edit to meals" );
        response.sendRedirect("meals");
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
