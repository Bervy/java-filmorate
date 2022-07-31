package ru.yandex.practicum.filmorate.logger;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
public class UserLogger {

    private UserLogger() {

    }

    public static void logUserNameIsEmpty(User user) {
        log.info("User name {} is empty, replaced with login ", user.getName());
    }

    public static void logUserWasAdded(User user) {
        log.info("User {} with id {} was added.", user.getLogin(), user.getId());
    }

    public static void logUserWasUpdated(User user) {
        log.info("User {} with id {} and email {} was updated.", user.getLogin(), user.getId(), user.getEmail());
    }

    public static void logUserLoginContainsWhiteSpaces(String login) {
        log.info("User login {} contains white spaces", login);
    }
}