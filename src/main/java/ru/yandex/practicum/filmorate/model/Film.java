package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.model.annotations.ReleaseDateAfter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Film {
    private Long id;
    @NotBlank
    @NonNull
    private final String name;
    @Size(max = 200)
    private final String description;
    @Min(1)
    private final Long duration;
    @ReleaseDateAfter
    private final LocalDate releaseDate;
    private final Rating mpa;
    private final Set<Long> likes = new HashSet<>();
    private List<Genre> genres;

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("film_name", name);
        values.put("description", description);
        values.put("duration", duration);
        values.put("release_date", releaseDate);
        values.put("mpa_id", mpa.getId());
        return values;
    }
}