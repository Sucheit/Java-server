package ru.myapp.utils;

import java.time.format.DateTimeFormatter;

public class Constants {

    private Constants() {
        throw new RuntimeException();
    }

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

}
