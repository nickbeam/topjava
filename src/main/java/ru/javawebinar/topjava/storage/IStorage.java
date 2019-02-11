package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.MealTo;

public interface IStorage {

    MealTo get(String id);

    void update(MealTo meal);

    void save(MealTo meal);

    void delete(String id);
}
