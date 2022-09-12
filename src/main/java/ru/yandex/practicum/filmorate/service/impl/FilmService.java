package ru.yandex.practicum.filmorate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmorateNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmDao;
import ru.yandex.practicum.filmorate.repository.RatingDao;
import ru.yandex.practicum.filmorate.repository.UserDao;
import ru.yandex.practicum.filmorate.service.AbstractService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.exceptions.ExceptionDescriptions.*;

@Service
@Slf4j
public class FilmService implements AbstractService<Film> {

    private FilmDao filmDao;
    private RatingDao ratingDao;
    private UserDao userDao;

    @Override
    public List<Film> findAll() {
        return filmDao.findAll();
    }

    @Override
    public Optional<Film> findById(Long filmId) {
        return filmDao.findById(filmId);
    }

    @Override
    public Optional<Film> save(Film film) {
        filmValidation(film);
        return filmDao.save(film);
    }

    @Override
    public Optional<Film> update(Film film) {
        filmValidation(film);
        filmExistsValidation(film);
        return filmDao.update(film);
    }

    @Override
    public void delete(Long filmId) {
        filmIdExistsValidation(filmId);
        filmDao.delete(filmId);
    }

    public void addLike(long filmId, long userId) {
        filmIdExistsValidation(filmId);
        userIdExistsValidation(userId);
        filmDao.addLike(filmId, userId);
    }

    public void deleteLike(long filmId, long userId) {
        filmIdExistsValidation(filmId);
        userIdExistsValidation(userId);
        filmDao.deleteLike(filmId, userId);
    }

    public List<Film> getSortedFilmsByLikes(Long count) {
        if (count < 0) {
            throw new FilmorateNotFoundException(WRONG_POPULAR_FILMS_COUNT.getTitle());
        }
        if (count == 0) {
            count = 10L;
        }
        return filmDao.getSortedFilmsByLikes(count);
    }

    private void filmExistsValidation(Film film) {
        if (film.getId() < 0 || filmDao.findById(film.getId()).isEmpty()) {
            throw new FilmorateNotFoundException(String.format(
                    FILM_NOT_FOUND.getTitle(), film.getId()));
        }
    }

    private void filmIdExistsValidation(Long filmId) {
        if (filmDao.findById(filmId).isEmpty()) {
            throw new FilmorateNotFoundException(String.format(
                    FILM_NOT_FOUND.getTitle(), filmId));
        }
    }

    private void userIdExistsValidation(Long userId) {
        if (userDao.findById(userId).isEmpty()) {
            throw new FilmorateNotFoundException(String.format(
                    USER_NOT_FOUND.getTitle(), userId));
        }
    }

    private void filmValidation(Film film) {
        if (film.getMpa() == null) {
            throw new ValidationException(EMPTY_RATING.getTitle());
        }
        if (film.getReleaseDate() == null) {
            throw new ValidationException(EMPTY_RELEASE_DATE.getTitle());
        }
        if (film.getGenres() == null) {
            film.setGenres(new ArrayList<>());
        } else {
            film.setGenres(film.getGenres().stream().distinct().collect(Collectors.toList()));
        }
        try {
            ratingDao.getRatingById(film.getMpa().getId());
        } catch (DataAccessException d) {
            throw new FilmorateNotFoundException(RATING_NOT_FOUND.getTitle());
        }
    }

    @Autowired
    @Qualifier("filmImpl")
    public void setFilmDao(FilmDao filmDao) {
        this.filmDao = filmDao;
    }

    @Autowired
    @Qualifier("ratingImpl")
    public void setRatingDao(RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    @Autowired
    @Qualifier("userImpl")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}