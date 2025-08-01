package ru.myapp.utils;

import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

  public static final String API_PREFIX = "/api";

  public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
      DATE_TIME_PATTERN);

  public static final int BATCH_SIZE = 1000;

}
