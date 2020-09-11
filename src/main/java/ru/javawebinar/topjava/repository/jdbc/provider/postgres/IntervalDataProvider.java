package ru.javawebinar.topjava.repository.jdbc.provider.postgres;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.jdbc.BetweenIntervalService;

import java.time.LocalDateTime;
import java.util.List;

public class IntervalDataProvider implements BetweenIntervalService {
    @Override
    public LocalDateTime getDate(LocalDateTime date) {
        return date;
    }
}
