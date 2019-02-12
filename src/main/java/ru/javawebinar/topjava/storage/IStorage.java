package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface IStorage {

    Meal get(String id);

    void update(Meal meal);

    void save(Meal meal);

    void delete(String id);

    List<Meal> getAll();
}
