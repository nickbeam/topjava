package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.meal.AbstractMealController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {

    public JspMealController(MealService service) {
        super(service);
    }
//    private MealRestController mealController;
//
//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        WebApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
//        mealController = springContext.getBean(MealRestController.class);
//    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        String action = request.getParameter("action");
//        if (action == null) {
//            Meal meal = new Meal(
//                    LocalDateTime.parse(request.getParameter("dateTime")),
//                    request.getParameter("description"),
//                    Integer.parseInt(request.getParameter("calories")));
//
//            if (StringUtils.isEmpty(request.getParameter("id"))) {
//                mealController.create(meal);
//            } else {
//                mealController.update(meal, getId(request));
//            }
//            response.sendRedirect("meals");
//
//        } else if ("filter".equals(action)) {
//            LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
//            LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
//            LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
//            LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
//            request.setAttribute("meals", mealController.getBetween(startDate, startTime, endDate, endTime));
//            request.getRequestDispatcher("/meals.jsp").forward(request, response);
//        }
//    }

//    @RequestMapping()


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getRoot() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @RequestMapping(value = "{id}/update", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, Model model) {
        model.addAttribute("meal", super.get(id));
        return "mealForm";
    }

    @RequestMapping(value = "{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int id, Model model) {
        super.delete(id);
        return "redirect:/meals";
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");
//
//        switch (action == null ? "all" : action) {
//            case "delete":
//                int id = getId(request);
//                mealController.delete(id);
//                response.sendRedirect("meals");
//                break;
//            case "create":
//            case "update":
//                final Meal meal = "create".equals(action) ?
//                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
//                        mealController.get(getId(request));
//                request.setAttribute("meal", meal);
//                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
//                break;
//            case "all":
//            default:
//                request.setAttribute("meals", mealController.getAll());
//                request.getRequestDispatcher("/meals.jsp").forward(request, response);
//                break;
//        }
//    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
