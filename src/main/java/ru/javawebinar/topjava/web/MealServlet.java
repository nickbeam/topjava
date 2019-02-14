package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.IStorage;
import ru.javawebinar.topjava.storage.MapStorage;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IStorage storage;

    private static final Logger log = getLogger(UserServlet.class);

    public MealServlet() {
        super();
        storage = new MapStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //Integer id = getId(request);
        LocalDateTime dateTime = TimeUtil.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, calories);

        storage.save(meal);

        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        //Integer id = getId(request);
        switch (action) {
            case "edit":
                log.debug("edit meal");
                request.setAttribute("meal", storage.get(getId(request)));
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
            case "delete":
                log.debug("delete meal");
                storage.delete(getId(request));
                break;
            case "insert":
                log.debug("insert new meal");
                Meal meal = new Meal(TimeUtil.now(), "", 0);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
        }
        log.debug("forward to meals");
        request.setAttribute("meals", MealsUtil.getFilteredWithExcess(storage.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000));
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
