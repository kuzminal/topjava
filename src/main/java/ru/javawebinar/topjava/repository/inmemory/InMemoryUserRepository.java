package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final ConcurrentMap<Integer, User> userStorage;
    private final AtomicInteger userId = new AtomicInteger();

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;

    public InMemoryUserRepository() {
        this.userStorage = new ConcurrentHashMap<>();
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return userStorage.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(userId.incrementAndGet());
            userStorage.put(user.getId(), user);
            return user;
        }
        return userStorage.computeIfPresent(user.getId(), (id, oldUser) -> user);
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
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return userStorage.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }
}
