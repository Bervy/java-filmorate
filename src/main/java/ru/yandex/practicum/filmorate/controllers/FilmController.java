package ru.yandex.practicum.filmorate.controllers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.logger.FilmLogger;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Data
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private long id;
    private Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        FilmLogger.logFilmWasAdded(film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            FilmLogger.logFilmWasUpdated(film);
            return film;
        } else {
            throw new FilmNotFoundException("List don't contains this film");
        }
    }

    private long generateId() {
        return ++id;
    }
}