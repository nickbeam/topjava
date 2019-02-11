package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        String id = request.getParameter("id");
        switch (action) {
            case "edit":
                log.debug("EDIT");
                request.setAttribute("meal", meals);
                break;
            case "delete":
                log.debug("DELETE");
                request.setAttribute("meals", meals);
                break;
            case "insert":
                log.debug("INSERT");
                request.setAttribute("meals", meals);
                break;
            case "":
                log.debug("forward to meals");
                request.setAttribute("meals", meals);
                break;
        }
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
