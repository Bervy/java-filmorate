package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.logger.UserLogger;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserNameValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Data
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private long id;

    @GetMapping
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        UserNameValidator.validate(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        UserLogger.logUserWasAdded(user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            UserNameValidator.validate(user);
            users.put(user.getId(), user);
            UserLogger.logUserWasUpdated(user);
            return user;
        } else {
            throw new UserNotFoundException("There wasn't user with such id in user list.");
        }
    }

    private long generateId() {
        return ++id;
    }
}