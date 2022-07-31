package ru.yandex.practicum.filmorate.logger;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;

@Slf4j
public class FilmLogger {

    private FilmLogger() {

    }

    public static void logFilmWasAdded(Film film) {
        log.info("Film {} with id {} released {} was added", film.getName(), film.getId(), film.getReleaseDate());
    }

    public static void logFilmWasUpdated(Film film) {
        log.info("Film {} with id {} released {} was updated", film.getName(), film.getId(), film.getReleaseDate());
    }
}