package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;

public interface IStorage {

    Meal get(Integer id);

    Meal save(Meal meal);

    void delete(Integer id);

    Collection<Meal> getAll();
}
