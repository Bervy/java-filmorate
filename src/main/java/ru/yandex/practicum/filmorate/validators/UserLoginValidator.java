package ru.yandex.practicum.filmorate.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotNull;
import ru.yandex.practicum.filmorate.annotations.ContainsSpaces;
import ru.yandex.practicum.filmorate.logger.UserLogger;

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