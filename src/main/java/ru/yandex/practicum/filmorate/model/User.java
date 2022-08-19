package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.annotations.ContainsSpaces;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private long id = 0L;
    private Set<Long> friends = new HashSet<>();
    @NotBlank
    @Email
    private final String email;
    @NotBlank @ContainsSpaces
    private final String login;
    @NonNull
    private String name;
    @Past
    private final LocalDate birthday;
}
