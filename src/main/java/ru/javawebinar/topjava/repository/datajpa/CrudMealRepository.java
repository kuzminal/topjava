package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
//    @Query(name = User.DELETE)
    @Query("DELETE FROM Meal m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime>=:startDateTime AND m.dateTime<:endDateTime ORDER BY m.dateTime DESC")
    Meal getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);
}
