package ru.yandex.practicum.filmorate.validators;


import ru.yandex.practicum.filmorate.annotations.ReleaseDateAfter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateAfter, LocalDate> {

    @Override
    public boolean isValid(@NotNull LocalDate localDate, ConstraintValidatorContext context) {
        return localDate.isAfter(LocalDate.of(1895, 12, 27));
    }
}