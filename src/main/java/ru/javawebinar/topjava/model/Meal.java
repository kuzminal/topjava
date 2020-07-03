package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=?1 AND m.user.id=?2"),
        @NamedQuery(name = Meal.BY_ID, query = "SELECT m FROM Meal m WHERE m.user.id=?1 AND m.id=?2"),
        @NamedQuery(name = Meal.INCLUSIVE, query = "SELECT m FROM Meal m WHERE m.user.id=?1 AND m.dateTime >=?2 AND m.dateTime<?3 ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m WHERE m.user.id=?1 ORDER BY m.dateTime DESC"),
})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_unique_user_datetime_idx")})
public class Meal extends AbstractBaseEntity {
    public static final String DELETE = "Meal.delete";
    public static final String BY_ID = "Meal.getById";
    public static final String ALL_SORTED = "Meal.getAll";
    public static final String INCLUSIVE = "Meal.getBetweenInclusive";
    public static final String UPDATE = "Meal.update";

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp")
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false, columnDefinition = "meal's definition")
    @NotNull
    @Size(min = 2, max = 120)
    private String description;

    @Column(name = "calories", nullable = false, columnDefinition = "calories value")
    @Min(10)
    @Max(5000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    public Meal() {
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
