package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmorateNotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.repository.RatingDao;

import java.util.List;

import static ru.yandex.practicum.filmorate.exceptions.ExceptionDescriptions.RATING_NOT_FOUND;


@Service
public class MpaService {
    private final RatingDao ratingDao;

    @Autowired
    public MpaService(RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    public Rating findRatingById(long ratingId) {
        if (ratingId < 0) {
            throw new FilmorateNotFoundException(RATING_NOT_FOUND.getTitle());
        }
        return ratingDao.getRatingById(ratingId);
    }

    public List<Rating> findAll() {
        return ratingDao.getAllRatings();
    }
}