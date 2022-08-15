package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.annotations.ReleaseDateAfter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    @NotBlank
    @NonNull
    private final String name;
    @Size(max = 200)
    private final String description;
    @Min(1)
    private final long duration;
    @ReleaseDateAfter
    private final LocalDate releaseDate;
    private long id = 0L;
    private Set<Long> likes = new HashSet<>();
}