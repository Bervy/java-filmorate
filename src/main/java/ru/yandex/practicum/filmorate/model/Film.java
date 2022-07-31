package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.annotations.ReleaseDateAfter;

import java.time.LocalDate;

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
}