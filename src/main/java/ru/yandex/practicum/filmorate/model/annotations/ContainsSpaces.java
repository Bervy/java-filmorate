package ru.yandex.practicum.filmorate.model.annotations;

import ru.yandex.practicum.filmorate.model.validators.UserLoginValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({FIELD, ANNOTATION_TYPE, TYPE_USE})
@Constraint(validatedBy = UserLoginValidator.class)
public @interface ContainsSpaces {
    String message() default "{login.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}