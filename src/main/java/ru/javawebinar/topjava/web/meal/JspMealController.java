package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.meal.AbstractMealController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {

    @PostMapping(value = "/save")
    public String save(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.isEmpty(request.getParameter("id"))) {
            super.create(meal);
        } else {
            super.update(meal, getId(request));
        }
        return "redirect:/meals";
    }

    @PostMapping
    public String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals" , super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @GetMapping(value = "/")
    public String getRoot() {
        return "index";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @GetMapping(value = "{id}/update")
    public String update(@PathVariable("id") int id, Model model) {
        model.addAttribute("meal", super.get(id));
        return "mealForm";
    }

    @GetMapping(value = "{id}/delete")
    public String delete(@PathVariable("id") int id, Model model) {
        super.delete(id);
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
