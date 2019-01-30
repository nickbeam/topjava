package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();
        Map<LocalDate, Integer> dateExceed = new HashMap<>();
        Map<LocalDate, UserMeal> userMealMap = new HashMap<>();
        for (UserMeal mealRecord : mealList) {
            LocalTime lt = mealRecord.getDateTime().toLocalTime();
            LocalDate ld = mealRecord.getDateTime().toLocalDate();
            if (TimeUtil.isBetween(lt, startTime, endTime)) {
                userMealMap.put(ld, mealRecord);
            }
            if (!dateExceed.containsKey(ld)) {
                dateExceed.put(ld, mealRecord.getCalories());
            } else {
                dateExceed.computeIfPresent(ld, (k, v) -> v + mealRecord.getCalories());
            }
        }
        userMealMap.forEach((k, v) -> {
            userMealWithExceeds.add(new UserMealWithExceed(v.getDateTime(), v.getDescription(), v.getCalories(), dateExceed.get(k) > caloriesPerDay));
        });

        return userMealWithExceeds;
    }
}
