package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.FilmorateNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.*;

import static ru.yandex.practicum.filmorate.exceptions.ExceptionDescriptions.EMPTY_FILM_ID;
import static ru.yandex.practicum.filmorate.exceptions.ExceptionDescriptions.FILM_NOT_FOUND;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private static final String FILM_WITH_ID = "Film with id {}";
    private final Map<Long, Film> films = new HashMap<>();
    private long id = 0;

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmById(Long filmId) {
        if (filmId == null) {
            throw new FilmorateNotFoundException(String.valueOf(EMPTY_FILM_ID));
        }
        if (films.containsKey(filmId)) {
            log.info(FILM_WITH_ID + " was returned", filmId);
            return films.get(filmId);
        } else {
            throw new FilmorateNotFoundException(String.format(
                    String.valueOf(FILM_NOT_FOUND), filmId));
        }
    }

    @Override
    public Film create(Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        log.info(FILM_WITH_ID + " released {} was added",
                film.getId(),
                film.getReleaseDate());
        return film;
    }

    @Override
    public Film update(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info(FILM_WITH_ID + " released {} was updated",
                    film.getId(),
                    film.getReleaseDate());
            return film;
        } else {
            throw new FilmorateNotFoundException(String.format(
                    String.valueOf(FILM_NOT_FOUND), film.getId()));
        }
    }

    public Film delete(@Valid @RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            films.remove(film.getId());
            log.info("Film {} with id {} released {} was deleted",
                    film.getName(),
                    film.getId(),
                    film.getReleaseDate());
            return film;
        } else {
            throw new FilmorateNotFoundException(String.format(
                    String.valueOf(FILM_NOT_FOUND), film.getId()));
        }
    }

    public Map<Long, Film> getFilms() {
        return Collections.unmodifiableMap(films);
    }

    private long generateId() {
        return ++id;
    }
}