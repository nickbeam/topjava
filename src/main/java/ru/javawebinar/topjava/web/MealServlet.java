package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.IStorage;
import ru.javawebinar.topjava.storage.MapMealStorage;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IStorage storage;

    private static final Logger log = getLogger(UserServlet.class);

    public MealServlet() {
        super();
        storage = new MapMealStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        LocalDateTime dateTime = TimeUtil.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(id, dateTime, description, calories);
        if (storage.get(id) == null) {
            storage.save(meal);
        } else {
            storage.update(meal);
        }

        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        String id = request.getParameter("id");
        switch (action) {
            case "edit":
                log.debug("edit meal: " + id);
                request.setAttribute("meal", storage.get(id));
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
            case "delete":
                log.debug("delete meal: " + id);
                storage.delete(id);
                break;
            case "insert":
                log.debug("insert meal: " + id);
                Meal meal = new Meal(TimeUtil.now(), "", 0);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
        }
        log.debug("forward to meals");
        request.setAttribute("meals", MealsUtil.getFilteredWithExcess(storage.getAll(), LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
