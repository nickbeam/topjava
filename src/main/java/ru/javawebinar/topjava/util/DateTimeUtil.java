package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetween(T dateTime, T startDateTime, T endDateTime) {
        return  dateTime.compareTo(startDateTime) >= 0 && dateTime.compareTo(endDateTime) <= 0;
    }

    public static LocalDate parseDate(String strDate) {
        if (strDate == null || strDate.equals("")) {
            return null;
        }
        return LocalDate.parse(strDate);
    }

    public static LocalTime parseTime(String strTime) {
        if (strTime == null || strTime.equals("")) {
            return null;
        }
        return LocalTime.parse(strTime);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
