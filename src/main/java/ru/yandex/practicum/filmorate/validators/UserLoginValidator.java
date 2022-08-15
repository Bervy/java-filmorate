package ru.yandex.practicum.filmorate.validators;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.annotations.ContainsSpaces;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

@Slf4j
public class UserLoginValidator implements ConstraintValidator<ContainsSpaces, String> {

    @Override
    public boolean isValid(@NotNull String login, ConstraintValidatorContext context) {
        if (login.contains(" ")) {
            log.info("User login {} contains white spaces", login);
            return false;
        }
        return true;
    }
}