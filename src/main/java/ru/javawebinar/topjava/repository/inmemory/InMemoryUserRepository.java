package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final ConcurrentMap<Integer, User> userStorage;
    private final AtomicInteger userId = new AtomicInteger();

    public InMemoryUserRepository() {
        this.userStorage = new ConcurrentHashMap<>();
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        User storedUser = userStorage.remove(id);
        if (storedUser != null) {
            return true;
        } else return false;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.getId() == null) {
            user.setId(userId.incrementAndGet());
        }
        return userStorage.put(user.getId(), user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userStorage.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return userStorage.values().stream()
                .sorted(this::compareForSorting)
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return userStorage.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    private int compareForSorting(User user1, User user2) {
        String x1 = user1.getName();
        String x2 = user2.getName();
        int sComp = x1.compareTo(x2);
        if (sComp != 0) {
            return sComp;
        }
        String y1 = user1.getEmail();
        String y2 = user2.getEmail();
        return y1.compareTo(y2);
    }
}
