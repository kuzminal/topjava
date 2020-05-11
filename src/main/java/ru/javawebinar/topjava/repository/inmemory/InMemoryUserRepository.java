package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collections;
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
    private static final AtomicInteger userId = new AtomicInteger();

    public InMemoryUserRepository() {
        this.userStorage = new ConcurrentHashMap<>();
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        userStorage.remove(id);
        return true;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.getId() == null) {
             user.setId(userId.incrementAndGet());
        }
        userStorage.putIfAbsent(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        userStorage.get(id);
        return null;
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return userStorage.values().stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return userStorage.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().get();
    }
}
