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
        MealDao mealStorage = storage;
        switch (action) {
            case "delete" :
                storage.delete(Long.parseLong(mealId));
                response.sendRedirect("meals");
                return;
            case "edit" :
                request.setAttribute("meal", mealStorage.getById(Long.parseLong(mealId)));
                request.getRequestDispatcher("/mealsEdit.jsp").forward(request, response);
                break;
        }
        List<MealTo> mealsTo = MealsUtil.convertMealsToTO(mealStorage.getAll());
        mealsTo.sort(Comparator.comparing(MealTo::getDateTime));
        request.setAttribute("meals", mealsTo);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
