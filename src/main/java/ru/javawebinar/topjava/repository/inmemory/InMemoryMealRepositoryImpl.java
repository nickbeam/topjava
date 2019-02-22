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
        repository.putIfAbsent(userId, new HashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else if (repository.get(userId).get(meal.getId()) == null){
            return null;
        }
        repository.get(userId).put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        if (repository.get(userId) == null || repository.get(userId).get(id) == null) {
            return false;
        }
        repository.get(userId).remove(id);
        return true;
    }

    @Override
    public Meal get(int userId, int id) {
        repository.putIfAbsent(userId, new HashMap<>());
        return repository.get(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFiltered(userId, null, null, null, null);
    }

    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        if (repository.get(userId) == null) {
            return Collections.emptyList();
        }
        return repository.get(userId).values().stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate)
                        && DateTimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}
