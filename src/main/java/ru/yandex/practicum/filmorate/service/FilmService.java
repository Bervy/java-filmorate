package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmorateAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.FilmorateNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.exceptions.ExceptionDescriptions.*;

@Service
@Slf4j
public class FilmService {

    private FilmStorage filmStorage;

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film getFilmById(Long filmId) {
        if (filmId == null) {
            throw new FilmorateNotFoundException(String.valueOf(EMPTY_FILM_ID));
        }
        return filmStorage.getFilmById(filmId);
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Film delete(Film film) {
        return filmStorage.delete(film);
    }

    public void addLike(long filmId, long userId) {
        Set<Long> filmLikes = getValidLikesByFilmId(filmId);
        if (filmLikes.contains(userId)) {
            throw new FilmorateAlreadyExistsException(String.format(
                    String.valueOf(LIKE_ALREADY_EXISTS), filmId, userId));
        } else {
            filmLikes.add(userId);
            log.info("Film with id {} added like from user with id {}", filmId, userId);
        }
    }

    public void deleteLike(long filmId, long userId) {
        Set<Long> filmLikes = getValidLikesByFilmId(filmId);
        if (filmLikes.contains(userId)) {
            filmLikes.remove(userId);
            log.info("Film with id {} deleted like from user with id {}", filmId, userId);
        } else {
            throw new FilmorateNotFoundException(String.format(
                    String.valueOf(LIKE_NOT_FOUND), filmId, userId));
        }
    }

    public List<Film> getSortedFilmsByLikes(Long count) {
        return filmStorage.findAll().stream()
                .sorted(((o1, o2) -> o2.getLikes().size() - o1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    private boolean isFilmIdExistsInFilms(long filmId) {
        return filmStorage.getFilms().containsKey(filmId);
    }

    private Set<Long> getValidLikesByFilmId(long filmId) {
        if (isFilmIdExistsInFilms(filmId)) {
            return filmStorage.getFilms().get(filmId).getLikes();
        }
        throw new FilmorateNotFoundException(String.format(
                String.valueOf(FILM_NOT_FOUND), filmId));
    }

    @Autowired
    public void setFilmStorage(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }
}