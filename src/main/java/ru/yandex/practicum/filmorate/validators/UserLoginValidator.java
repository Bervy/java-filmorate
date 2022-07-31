package ru.yandex.practicum.filmorate.validators;

import ru.yandex.practicum.filmorate.annotations.ContainsSpaces;
import ru.yandex.practicum.filmorate.logger.UserLogger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

public class UserLoginValidator implements ConstraintValidator<ContainsSpaces, String> {

    @Override
    public boolean isValid(@NotNull String login, ConstraintValidatorContext context) {
        if (login.contains(" ")) {
            UserLogger.logUserLoginContainsWhiteSpaces(login);
            return false;
        }
        return true;
    }
}