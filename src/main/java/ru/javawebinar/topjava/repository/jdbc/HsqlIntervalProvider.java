package ru.javawebinar.topjava.repository.jdbc;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.jdbc.BetweenIntervalService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class HsqlIntervalProvider extends BetweenIntervalService {

    @Override
    public  <T> T getStartDate(LocalDateTime startDate) {
        return (T) Timestamp.valueOf(startDate);
    }

    @Override
    public  <T> T getEndDate(LocalDateTime endDate) {
        return (T) Timestamp.valueOf(endDate);
    }
}
