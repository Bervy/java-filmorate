package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserNameValidator;

import javax.validation.Valid;
import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long id = 0;

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUser(Long userId) {
        if (userId == null) {
            throw new UserNotFoundException("Вы не ввели id пользователя");
        }
        if (users.containsKey(userId)) {
            log.info("User with id {} was returned", userId);
            return users.get(userId);
        } else {
            throw new FilmNotFoundException("Пользователь с id " + userId +
                    " не найден");
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
            throw new UserNotFoundException("There wasn't user in list.");
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
            throw new UserNotFoundException("There wasn't user in list.");
        }
    }

    public Map<Long, User> getUsers() {
        return Collections.unmodifiableMap(users);
    }

    private long generateId() {
        return ++id;
    }

}