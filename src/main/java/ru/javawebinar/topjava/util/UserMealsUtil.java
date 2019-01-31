package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

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
        for (UserMeal mealRecord : mealList) {
            LocalDate ld = mealRecord.getDateTime().toLocalDate();
            dateExceed.merge(ld, mealRecord.getCalories(), (oldVal, newVal) -> oldVal + newVal);
        }
        for (UserMeal mealRecord : mealList) {
            LocalTime lt = mealRecord.getDateTime().toLocalTime();
            if (TimeUtil.isBetween(lt, startTime, endTime)) {
                userMealWithExceeds.add(new UserMealWithExceed(mealRecord.getDateTime(), mealRecord.getDescription(), mealRecord.getCalories(), dateExceed.get(mealRecord.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }

        Map<LocalDate, Integer> dateExceedFromStream = mealList.stream()
                .collect(Collectors.groupingBy(mr -> mr.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream()
                .filter(mr -> TimeUtil.isBetween(mr.getDateTime().toLocalTime(), startTime, endTime))
                .map(mrf -> new UserMealWithExceed(mrf.getDateTime(), mrf.getDescription(), mrf.getCalories(),
                        dateExceedFromStream.get(mrf.getDateTime().toLocalDate()) > caloriesPerDay)).collect(Collectors.toList());
    }
}
