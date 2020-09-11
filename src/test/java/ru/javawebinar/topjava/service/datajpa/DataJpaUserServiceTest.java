package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    @Transactional
    public void getWithMeal() throws Exception {
        User user = service.get(USER_ID);
        if (user.getMeals().size() != 0) {
            MEAL_MATCHER.assertMatch(user.getMeals(), MEALS);
        }
        assertMatch(user, USER);
    }
}
