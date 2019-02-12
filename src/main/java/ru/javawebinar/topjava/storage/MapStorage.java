package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MapStorage implements IStorage {

    private static final Map<String, Meal> meals = new ConcurrentHashMap<String, Meal>() {{
        put("1", new Meal("1", LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        put("2", new Meal("2", LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        put("3", new Meal("3",LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        put("4", new Meal("4", LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        put("5", new Meal("5",LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        put("6", new Meal("6", LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }};

    private void setNewId(Meal meal) {
        meal.setId(UUID.randomUUID().toString());
    }

    @Override
    public Meal get(String id) {
        return meals.get(id);
    }

    @Override
    public void update(Meal meal) {
        if (isExist(getSearchKey(meal.getId()))) {
            meals.put(meal.getId(), meal);
        }
    }

    @Override
    public Meal save(Meal meal) {
        if (!isExist(getSearchKey(meal.getId()))) {
            setNewId(meal);
            meals.put(meal.getId(), meal);
            return meal;
        }
        return null;
    }

    @Override
    public void delete(String id) {
        if (isExist(getSearchKey(id))) {
            meals.remove(id);
        }
    }

    private Meal getSearchKey(String id) {
        return meals.get(id);
    }

    private boolean isExist(Meal meal) {
        return meal != null;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }
}
