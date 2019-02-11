package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static LocalDateTime parse(String str) {
        return LocalDateTime.parse(str.replaceAll("T", " "), formatter);
    }

    public static LocalDateTime now() {
        return LocalDateTime.now().withSecond(0).withNano(0);
    }
}
