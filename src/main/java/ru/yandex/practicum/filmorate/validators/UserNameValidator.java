package ru.yandex.practicum.filmorate.validators;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
public class UserNameValidator {

    private UserNameValidator() {

    }

    public static void validate(User user) {
        if (isNameBlank(user)) {
            user.setName(user.getLogin());
            log.info("User name {} is empty, replaced with login ", user.getName());
        }
    }

    private static boolean isNameBlank(User user) {
        return user.getName().isBlank();
    }
}