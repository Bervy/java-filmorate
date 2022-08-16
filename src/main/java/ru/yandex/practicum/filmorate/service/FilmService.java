package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmorateAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.FilmorateNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Math.min;
import static ru.yandex.practicum.filmorate.exceptions.ExceptionDescriptions.*;

@Service
@Slf4j
public class FilmService {

    private FilmStorage filmStorage;

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
        List<Film> films = new ArrayList<>(filmStorage.findAll());
        if (films.isEmpty()) {
            return new ArrayList<>();
        }
        List<Film> sortedFilmsByLikes = films.stream()
                .sorted(((o1, o2) -> o2.getLikes().size() - o1.getLikes().size()))
                .collect(Collectors.toList());
        if (count == null) {
            return sortedFilmsByLikes.subList(0, min(sortedFilmsByLikes.size(), 10));
        } else {
            return sortedFilmsByLikes.subList(0, (int) min(sortedFilmsByLikes.size(), count));
        }
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

    public FilmStorage getFilmStorage() {
        return filmStorage;
    }

    @Autowired
    public void setFilmStorage(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }
}