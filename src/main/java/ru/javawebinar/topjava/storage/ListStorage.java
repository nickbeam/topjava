package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListStorage implements IStorage {

    public static final List<MealTo> meals = new ArrayList<>(Arrays.asList(
            new MealTo(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, false),
            new MealTo(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, false),
            new MealTo(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, false),
            new MealTo(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, true),
            new MealTo(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, true),
            new MealTo(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, true)
    ));



    private Integer getIndex(String id){
        for (int i = 0; i < meals.size(); i++) {
            if (meals.get(i).getId().equals(id)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public MealTo get(String id) {
        return meals.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void update(MealTo meal) {
        meals.set(getIndex(meal.getId()), meal);
    }

    @Override
    public void save(MealTo meal) {
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
}
