package ru.yandex.practicum.filmorate.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotNull;
import ru.yandex.practicum.filmorate.annotations.ReleaseDateAfter;

import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateAfter, LocalDate> {

    @Override
    public boolean isValid(@NotNull LocalDate localDate, ConstraintValidatorContext context) {
        return localDate.isAfter(LocalDate.of(1895, 12, 27));
    }
}