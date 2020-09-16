package ru.javawebinar.topjava.repository.jdbc;

import java.time.LocalDateTime;

public class PostgresIntervalProvider extends BetweenIntervalService {

    @Override
    public  <T> T getStartDate(LocalDateTime startDate) {
        return (T) startDate;
    }

    @Override
    public <T> T getEndDate(LocalDateTime endDate) {
        return (T) endDate;
    }
}
