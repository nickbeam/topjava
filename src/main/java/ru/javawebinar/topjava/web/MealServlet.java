package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.IStorage;
import ru.javawebinar.topjava.storage.ListStorage;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.storage.ListStorage.meals;

public class MealServlet extends HttpServlet {
    private final IStorage storage;

    private static final Logger log = getLogger(UserServlet.class);

    public MealServlet() {
        super();
        storage = new ListStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        LocalDateTime dateTime = TimeUtil.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        boolean excess = request.getParameter("excess") != null;
        MealTo meal = new MealTo(id, dateTime, description, calories, excess);
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
                request.setAttribute("meals", meals);
                break;
            case "insert":
                log.debug("insert meal: " + id);
                MealTo meal = new MealTo(TimeUtil.now(), "", 0, false);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
            case "":
                log.debug("forward to meals");
                request.setAttribute("meals", meals);
                break;
            default:
                request.setAttribute("meals", meals);
                break;
        }
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
