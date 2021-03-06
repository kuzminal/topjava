package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final LocalDateTime MIN_DATE = LocalDateTime.of(1,1,1, 0, 0);
    private static final LocalDateTime MAX_DATE = LocalDateTime.of(3000,1,1, 0, 0);

    public static LocalDateTime getStarInclusive(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay() : MIN_DATE;
    }

    public static LocalDateTime getEndInclusive(LocalDate localDate) {
        return localDate != null ? localDate.plus(1, ChronoUnit.DAYS).atStartOfDay() : MAX_DATE;
    }

    public static String toString(LocalDateTime localDateTime) {
        return localDateTime == null ? "" : localDateTime.format(DATE_TIME_FORMATTER);
    }

    public static @Nullable LocalDate parseLocalDate(@Nullable String localDate) {
        return StringUtils.isEmpty(localDate) ? null : LocalDate.parse(localDate);
    }

    public static @Nullable LocalTime parseLocalTime(@Nullable String localTime) {
        return StringUtils.isEmpty(localTime) ? null : LocalTime.parse(localTime);
    }
}
