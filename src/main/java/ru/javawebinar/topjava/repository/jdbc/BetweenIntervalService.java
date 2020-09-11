package ru.javawebinar.topjava.repository.jdbc;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface BetweenIntervalService {
    <T>T getDate(LocalDateTime date);
}
