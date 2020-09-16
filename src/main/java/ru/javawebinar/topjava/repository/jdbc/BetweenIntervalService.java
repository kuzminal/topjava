package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public abstract class BetweenIntervalService {
    public String getQuery() {
        return "SELECT * FROM meals WHERE user_id=?  AND date_time >=  ? AND date_time < ? ORDER BY date_time DESC";
    }

    public abstract <T> T getStartDate(LocalDateTime dateTime);

    public abstract <T> T getEndDate(LocalDateTime dateTime);

    public RowMapper<Meal> getRowMapper() {
        return BeanPropertyRowMapper.newInstance(Meal.class);
    }

    public List<Meal> makeQuery(JdbcTemplate jdbcTemplate, LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return jdbcTemplate.query(getQuery(), getRowMapper(), getStartDate(startDateTime), getEndDate(endDateTime), userId);
    }
}
