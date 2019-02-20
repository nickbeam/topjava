package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(1, meal));
        MealsUtil.MEALS.forEach(meal -> save(2, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        if (!repository.containsKey(userId)) {
            repository.put(userId, new HashMap<>());
        }
        repository.get(userId).put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        if (!repository.containsKey(userId) || !repository.get(userId).containsKey(id)) {
            return false;
        }
        repository.get(userId).remove(id);
        return true;
    }

    @Override
    public Meal get(int userId, int id) {
        return repository.get(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAll(userId, null, null, null, null);
    }

    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        if (!repository.containsKey(userId)) {
            return Collections.emptyList();
        }
        return repository.get(userId).values().stream()
                .filter(meal -> DateTimeUtil.isBetweenDate(meal.getDate(), startDate != null ? startDate : LocalDate.MIN, endDate != null ? endDate : LocalDate.MAX)
                        && DateTimeUtil.isBetweenTime(meal.getTime(), startTime != null ? startTime : LocalTime.MIN, endTime != null ? endTime : LocalTime.MAX))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}
