package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private MealRestController mealRestController;
    private Map<String,String> filterParams;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            mealRestController = appCtx.getBean(MealRestController.class);
        }
        filterParams = new HashMap<>();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String mealId = request.getParameter("mealId") != null ? request.getParameter("mealId") : "";
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        log.debug("Action = " + action + " mealId = " + mealId);
        switch (action) {
            case "delete":
                if (!mealId.equals("")) {
                    mealRestController.delete(Integer.parseInt(mealId));
                }
                response.sendRedirect("meals");
                return;
            case "edit":
                request.setAttribute("meal", mealRestController.get(Integer.parseInt(mealId)));
                request.getRequestDispatcher("/mealsEdit.jsp").forward(request, response);
                return;
            case "add":
                Meal meal = new Meal(null, LocalDateTime.now(), "", 0);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealsEdit.jsp").forward(request, response);
                return;
        }
        List<MealTo> mealsTo;
        if (filterParams.size() > 0) {
            mealsTo = mealRestController.filter(filterParams);
        } else {
            mealsTo = mealRestController.getAll();
        }
        request.setAttribute("meals", mealsTo);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String postAction = request.getParameter("action") != null ? request.getParameter("action") : "";
        String mealIdStr = request.getParameter("mealId") != null ? request.getParameter("mealId") : "";
        String sd = request.getParameter("dateStart") != null ? request.getParameter("dateStart") : "";
        String ed = request.getParameter("dateEnd") != null ? request.getParameter("dateEnd") : "";
        String st = request.getParameter("timeStart") != null ? request.getParameter("timeStart") : "";
        String et = request.getParameter("timeEnd") != null ? request.getParameter("timeEnd") : "";
        Integer mealId;
        if (!postAction.equals("filter")) {
            if (mealIdStr != "") {
                mealId = Integer.parseInt(mealIdStr);
            } else mealId = null;
            saveMeal(request, mealId);
        } else {
            filterParams.put("startDate", sd);
            filterParams.put("dateEnd", ed);
            filterParams.put("timeStart", st);
            filterParams.put("timeEnd", et);
        }
        response.sendRedirect("meals");
    }

    private void saveMeal(HttpServletRequest request, Integer mealId) {
        String description = request.getParameter("description") != null ? request.getParameter("description") : "";
        String calories = request.getParameter("calories") != null ? request.getParameter("calories") : "";
        String dateTime = request.getParameter("dateTime") != null ? request.getParameter("dateTime") : "";
        if (description.trim().length() != 0 && calories.trim().length() != 0 && dateTime.trim().length() != 0) {
            Meal meal = new Meal(mealId, LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
            if (meal.isNew()) {
                mealRestController.save(meal);
            } else mealRestController.update(meal, mealId);
        }
    }
}
