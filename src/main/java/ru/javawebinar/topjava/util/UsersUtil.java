package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(1, "Admin", "admin@mail.com", "123", Role.ROLE_ADMIN),
            new User(2, "User", "user@mail.com", "123", Role.ROLE_USER)
    );
}
