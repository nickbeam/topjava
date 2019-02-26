package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.of(2019, Month.FEBRUARY, 20, 13, 00), "Обед2019", 950);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal, MEAL3, MEAL2, MEAL1);
    }

    @Test(expected = DuplicateKeyException.class)
    public void createDuplicateRecordUserIdDateTime() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "NewMeal", 333);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.get(created.getId(), USER_ID), newMeal);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL3, MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void deletedWrongUserFood() throws Exception {
        service.delete(MEAL1.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL1.getId(), USER_ID);
        assertMatch(meal, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getWrongUserFound() throws Exception {
        service.get(MEAL1.getId(), ADMIN_ID);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> list = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 31, 10, 0),
                LocalDateTime.of(2015, Month.MAY, 31, 13, 0), ADMIN_ID);
        assertMatch(list, MEAL5, MEAL4);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL1.getId(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);;
        updated.setCalories(1555);
        updated.setDescription("Updated");
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1.getId(), USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateWrongUserFood() throws Exception {
        Meal updated = new Meal(MEAL1.getId(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);;
        updated.setCalories(1555);
        updated.setDescription("Updated");
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1.getId(), ADMIN_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void getAllWrongUser() throws Exception {
        List<Meal> all = service.getAll(1);
        assertMatch(all, Collections.emptyList());
    }
}