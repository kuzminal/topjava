package ru.javawebinar.topjava.repository.jdbc.provider.hsql;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.jdbc.BetweenIntervalService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class IntervalDataProvider implements BetweenIntervalService {
    @Override
    public Timestamp getDate(LocalDateTime date) {
        return Timestamp.valueOf(date);
    }
}
