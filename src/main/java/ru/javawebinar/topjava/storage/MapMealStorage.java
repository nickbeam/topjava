package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public class MapMealStorage implements IStorage {

    private static final Map<String, Meal> meals = new HashMap<String, Meal>() {{
        put("1", new Meal("1", LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        put("2", new Meal("2", LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        put("3", new Meal("3",LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        put("4", new Meal("4", LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        put("5", new Meal("5",LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        put("6", new Meal("6", LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }};

    private String newId() {
        return UUID.randomUUID().toString();
    }

    private Integer getIndex(String id) {
        for (int i = 0; i < meals.size(); i++) {
            if (meals.get(i).getId().equals(id)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public Meal get(String id) {
        return meals.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void update(Meal meal) {
        Integer index = getIndex(meal.getId());
        if (index != null) {
            meals.set(index, meal);
        }
    }

    @Override
    public void save(Meal meal) {
        if (getIndex(meal.getId()) == null) {
            meals.add(meal);
        }
    }

    @Override
    public void delete(String id) {
        Integer index = getIndex(id);
        if (index != null) {
            meals.remove(index.intValue());
        }
    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }
}
