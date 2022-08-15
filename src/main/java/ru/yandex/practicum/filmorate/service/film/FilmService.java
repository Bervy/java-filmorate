package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.CountFilmException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.LikeNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Math.min;

@Service
@Slf4j
public class FilmService {

    private static final String LIKE_NOT_FOUND = "The movie with id %d dont have " +
            " like from user with id %d";
    private static final String FILM_NOT_FOUND = "The movie with id %d not found";
    FilmStorage filmStorage;

    public void addLike(long filmId, long userId) {
        Set<Long> filmLikes = getValidLikesByFilmId(filmId);
        if (filmLikes.contains(userId)) {
            throw new LikeNotFoundException(String.format(LIKE_NOT_FOUND, filmId, userId));
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
            throw new LikeNotFoundException(String.format(LIKE_NOT_FOUND, filmId, userId));
        }
    }

    public List<Film> getSortedFilmsByLikes(Long count) {
        List<Film> films = new ArrayList<>(filmStorage.getFilms().values());
        if (films.isEmpty()) {
            return new ArrayList<>();
        }
        List<Film> sortedFilmsByLikes = films.stream()
                .sorted(((o1, o2) -> o2.getLikes().size() - o1.getLikes().size()))
                .collect(Collectors.toList());
        if (count == null) {
            return sortedFilmsByLikes.subList(0, min(sortedFilmsByLikes.size(), 10));
        } else {
            if (isCountValid(count, sortedFilmsByLikes)) {
                return sortedFilmsByLikes.subList(0, Math.toIntExact(count));
            } else {
                throw new CountFilmException("The number of movies parameter is less than 0 "
                        + " or exceeds the number of films");
            }
        }
    }

    private boolean isFilmIdExistsInFilms(long filmId) {
        return filmStorage.getFilms().containsKey(filmId);
    }

    private Set<Long> getValidLikesByFilmId(long filmId) {
        if (isFilmIdExistsInFilms(filmId)) {
            return filmStorage.getFilms().get(filmId).getLikes();
        }
        throw new FilmNotFoundException(String.format(FILM_NOT_FOUND, filmId));
    }

    private boolean isCountValid(Long count, List<Film> sortedFilmsByLikes) {
        return count >= 0 && count < sortedFilmsByLikes.size();
    }

    public FilmStorage getFilmStorage() {
        return filmStorage;
    }

    @Autowired
    public void setFilmStorage(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }
}