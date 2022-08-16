package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.FilmorateNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserNameValidator;

import javax.validation.Valid;
import java.util.*;

import static ru.yandex.practicum.filmorate.exceptions.ExceptionDescriptions.EMPTY_USER_ID;
import static ru.yandex.practicum.filmorate.exceptions.ExceptionDescriptions.USER_NOT_FOUND;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long id = 0;

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(Long userId) {
        if (userId == null) {
            throw new FilmorateNotFoundException(String.valueOf(EMPTY_USER_ID));
        }
        if (users.containsKey(userId)) {
            log.info("User with id {} was returned", userId);
            return users.get(userId);
        } else {
            throw new FilmorateNotFoundException(String.format(
                    String.valueOf(USER_NOT_FOUND), userId));
        }
    }

    public User create(@Valid @RequestBody User user) {
        UserNameValidator.validate(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("User {} with id {} was added.",
                user.getLogin(),
                user.getId());
        return user;
    }

    public User update(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            UserNameValidator.validate(user);
            users.put(user.getId(), user);
            log.info("User {} with id {} and email {} was updated.",
                    user.getLogin(),
                    user.getId(),
                    user.getEmail());
            return user;
        } else {
            throw new FilmorateNotFoundException(String.format(
                    String.valueOf(USER_NOT_FOUND), user.getId()));
        }
    }

    public User delete(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            UserNameValidator.validate(user);
            users.remove(user.getId());
            log.info("User {} with id {} and email {} was deleted.",
                    user.getLogin(),
                    user.getId(),
                    user.getEmail());
            return user;
        } else {
            throw new FilmorateNotFoundException(String.format(
                    String.valueOf(USER_NOT_FOUND), user.getId()));
        }
    }

    public Map<Long, User> getUsers() {
        return Collections.unmodifiableMap(users);
    }

    private long generateId() {
        return ++id;
    }

}